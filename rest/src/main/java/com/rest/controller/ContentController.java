package com.rest.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.common.utils.ExceptionUtil;
import com.common.utils.ResponseResult;
import com.mapperutils.entity.TbContent;
import com.rest.service.ContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisCluster;


@RestController
@RequestMapping("/content")
public class ContentController {
	
	@Autowired
	private ContentService contentService;

	@Autowired
	private JedisCluster jedisCluster;

	/**
	 * 首页大广告位
	 * @param contentCategoryId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/list/{contentCategoryId}")
	public List<TbContent> getContentList(@PathVariable long contentCategoryId) throws Exception{
		return contentService.getContentList(contentCategoryId);
	}

	/**
	 * feign测试
	 * @return
	 */
	@RequestMapping("/test")
	public List<String> test(){
		List<String> list = new ArrayList<String>(Arrays.asList("A", "B", "C"));
		return list;
	}

	//测试Redis
	@RequestMapping("/setKey")
	public String setKey(){
		jedisCluster.set("abc", "123");
		return jedisCluster.get("abc");
	}
}
