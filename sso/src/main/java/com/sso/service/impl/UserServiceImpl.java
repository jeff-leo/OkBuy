package com.sso.service.impl;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.common.utils.CookieUtils;
import com.common.utils.ExceptionUtil;
import com.common.utils.JsonUtils;
import com.common.utils.ResponseResult;
import com.mapperutils.entity.TbUser;
import com.mapperutils.entity.TbUserExample;
import com.mapperutils.mapper.TbUserMapper;
import com.sso.dao.impl.JedisClientCluster;
import com.sso.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;


@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private TbUserMapper userMapper;
	@Autowired
	JedisClientCluster jedisClient;

	@Value("${REDIS_USER_SESSION_KEY}")
	private String REDIS_USER_SESSION_KEY;
	@Value("${SSO_SESSION_EXPIRE}")
	private int SSO_SESSION_EXPIRE;

	@Override
	public ResponseResult checkData(String content, Integer type) {
		// 创建查询条件
		TbUserExample example = new TbUserExample();
		TbUserExample.Criteria criteria = example.createCriteria();
		// 对数据进行校验：1、2、3分别代表username、phone、email
		// 用户名校验
		if (1 == type) {
			criteria.andUsernameEqualTo(content);
			// 电话校验
		} else if (2 == type) {
			criteria.andPhoneEqualTo(content);
			// email校验
		} else {
			criteria.andEmailEqualTo(content);
		}
		// 执行查询
		List<TbUser> list = userMapper.selectByExample(example);
		if (list == null || list.size() == 0) {
			return ResponseResult.ok(true);
		}
		return ResponseResult.ok(false);
	}

	@Override
	public ResponseResult createUser(TbUser user) {
		user.setCreated(new Date());
		user.setUpdated(new Date());
		user.setPassword(DigestUtils.md5DigestAsHex(user.getPassword()
				.getBytes()));
		try {
			userMapper.insert(user);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseResult.build(500, ExceptionUtil.getStackTrace(e));
		}
		return ResponseResult.ok();
	}

	@Override
	public ResponseResult userLogin(HttpServletRequest request, HttpServletResponse response,
			String username, String password) {
		TbUserExample example = new TbUserExample();
		TbUserExample.Criteria criteria = example.createCriteria();
		criteria.andUsernameEqualTo(username);
		List<TbUser> list = userMapper.selectByExample(example);
		if (list == null || list.size() == 0) {
			return ResponseResult.build(400, "用户名或密码错误");
		}
		TbUser user = list.get(0);
		// 比对密码
		if (!DigestUtils.md5DigestAsHex(password.getBytes()).equals(
				user.getPassword())) {
			return ResponseResult.build(400, "用户名或密码错误");
		}
		// 生成token
		String token = UUID.randomUUID().toString();
		// 将token对应user信息保存在redis中
		user.setPassword(null);
		// 加":"可以目录分级
		jedisClient.set(REDIS_USER_SESSION_KEY + ":" + token,
				JsonUtils.objectToJson(user));
		jedisClient.expire(REDIS_USER_SESSION_KEY, SSO_SESSION_EXPIRE);

		CookieUtils.setCookie(request, response, "TT_TOKEN", token);
		// 返回token
		return ResponseResult.ok(token);
	}

	@Override
	public ResponseResult getUserByToken(String token) {
		String json = jedisClient.get(REDIS_USER_SESSION_KEY + ":" + token);
		if (json == null) {
			return ResponseResult.build(400, "token过期，请重新登录");
		}
		// 更新过期时间
		jedisClient.expire(REDIS_USER_SESSION_KEY + ":" + token,
				SSO_SESSION_EXPIRE);
		// 返回用户信息
		return ResponseResult.ok(JsonUtils.jsonToPojo(json, TbUser.class));

	}

	//安全退出，清空缓存
	@Override
	public ResponseResult logout(String token) {
		jedisClient.del(REDIS_USER_SESSION_KEY + ":" + token);
		return ResponseResult.ok();
	}

}
