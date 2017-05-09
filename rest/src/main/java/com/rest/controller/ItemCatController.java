package com.rest.controller;

import com.common.utils.JsonUtils;
import com.rest.po.CatResult;
import com.rest.service.ItemCatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class ItemCatController {

	@Autowired
	private ItemCatService itemCatService;

	/**
	 * 首页侧边栏分类，采用jsonp跨域请求
	 * @param callback
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/itemcat/all", 
			produces=MediaType.APPLICATION_JSON_VALUE + ";charset=utf-8")
	@ResponseBody
	public String getItemCatList(String callback) throws Exception{//定义callback，jsonp调用时参数为callback
	    CatResult catList = itemCatService.getCatList();
	    String json = JsonUtils.objectToJson(catList);
	    String result = callback + "(" + json + ");";
	    System.out.println(result);
		return result;	
	}
}
