package com.sso.service;

import com.common.utils.ResponseResult;
import com.mapperutils.entity.TbUser;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public interface UserService {
	ResponseResult checkData(String content, Integer type);
	ResponseResult createUser(TbUser user);
	ResponseResult userLogin(HttpServletRequest request, HttpServletResponse response, String username, String password);
	ResponseResult getUserByToken(String token);
	public ResponseResult logout(String token);
}
