package com.sso.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.common.utils.ExceptionUtil;
import com.common.utils.ResponseResult;
import com.mapperutils.entity.TbUser;
import com.sso.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;
	
	@RequestMapping("/showRegister")
	public String showRegister() {
		return "register";
	}
	
	@RequestMapping("/showLogin")
	public String showLogin(String redirect, Model model) {
		model.addAttribute("redirect", redirect);
		return "login";
	}

	/**
	 * 用于查询用户名是否被占用
	 * @param param
	 * @param type
	 * @param callback
	 * @return
	 */
	@RequestMapping("/check/{param}/{type}")
	@ResponseBody
	public Object checkData(@PathVariable String param,
			@PathVariable Integer type, String callback) {

		ResponseResult result = null;

		// 参数有效性校验
		if (StringUtils.isBlank(param)) {
			result = ResponseResult.build(400, "校验内容不能为空");
		}
		if (type == null) {
			result = ResponseResult.build(400, "校验内容类型不能为空");
		}
		if (type != 1 && type != 2 && type != 3) {
			result = ResponseResult.build(400, "校验内容类型错误");
		}
		// 校验出错
		if (null != result) {
			if (null != callback) {
				MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(
						result);
				mappingJacksonValue.setJsonpFunction(callback);
				return mappingJacksonValue;
			} else {
				return result;
			}
		}
		// 调用服务
		try {
			result = userService.checkData(param, type);

		} catch (Exception e) {
			result = ResponseResult.build(500, ExceptionUtil.getStackTrace(e));
		}

		if (null != callback) {
			MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(
					result);
			mappingJacksonValue.setJsonpFunction(callback);
			return mappingJacksonValue;
		} else {
			return result;
		}
	}

	// 创建用户
	@RequestMapping("/register")
	@ResponseBody
	public ResponseResult createUser(TbUser user) {
		try {
			ResponseResult result = userService.createUser(user);
			return result;
		} catch (Exception e) {
			return ResponseResult.build(500, ExceptionUtil.getStackTrace(e));
		}
	}

	// 登录
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	@ResponseBody
	public ResponseResult userLogin(HttpServletRequest request, HttpServletResponse response, String username, String password) {
		try {

			ResponseResult result = userService.userLogin(request, response, username, password);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseResult.build(500, ExceptionUtil.getStackTrace(e));
		}
	}

	@RequestMapping("/token/{token}")
	@ResponseBody
	public Object getUserByToken(@PathVariable String token, String callback) {
		ResponseResult result = null;
		try {
			result = userService.getUserByToken(token);
		} catch (Exception e) {
			e.printStackTrace();
			result = ResponseResult.build(500, ExceptionUtil.getStackTrace(e));
		}

		// 判断是否为jsonp调用
		if (StringUtils.isBlank(callback)) {
			return result;
		} else {
			MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(
					result);
			mappingJacksonValue.setJsonpFunction(callback);
			return mappingJacksonValue;
		}

	}

	/**
	 * 如何跨域重定向？
	 * @param token
	 * @return
	 */
	@RequestMapping("/logout")
	public Object logout(@CookieValue(value = "TT_TOKEN", required = true) String token){
		try {
			userService.logout(token);
			return "Location:http://localhost:8080";
		} catch (Exception e) {
			return ResponseResult.build(400, ExceptionUtil.getStackTrace(e));
		}
	}

}
