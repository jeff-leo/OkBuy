package com.search.controller;

import com.common.utils.ExceptionUtil;
import com.common.utils.ResponseResult;
import com.search.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping("/manager")
public class ItemImportController {
	
	@Autowired
	private ItemService itemService;
	
	/**
	 * 导入数据库数据到solr
	 */
	@RequestMapping("/importall")
	@ResponseBody
	public ResponseResult importAll(){
		try {
			ResponseResult result = itemService.importAllItem();
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseResult.build(500, ExceptionUtil.getStackTrace(e));
		}
	}

}
