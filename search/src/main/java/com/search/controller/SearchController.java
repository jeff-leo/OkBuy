package com.search.controller;

import com.common.entity.SearchResult;
import com.common.utils.ExceptionUtil;
import com.common.utils.ResponseResult;
import com.search.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/search")
public class SearchController {
	
	@Autowired
	private ItemService itemService;
	
	@RequestMapping(value="/query", method=RequestMethod.GET)
	@ResponseBody
	public ResponseResult search(@RequestParam("q") String queryString,
								 @RequestParam(defaultValue="1") int page,
								 @RequestParam(defaultValue="60") int rows ){
		try {
			if(queryString == null || queryString.equals("")){
				return ResponseResult.build(400, "查询条件不能为空");
			}
			//queryString = new String(queryString.getBytes("utf-8"), "utf-8");
			System.out.println(queryString);
			SearchResult search = itemService.search(queryString, page, rows);
			return ResponseResult.ok(search);
		} catch (Exception e) {
			return ResponseResult.build(500, ExceptionUtil.getStackTrace(e));
		}
	}
}
