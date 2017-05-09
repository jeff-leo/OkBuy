package com.rest.controller;

import com.common.utils.ResponseResult;
import com.mapperutils.entity.TbItem;
import com.mapperutils.entity.TbItemDesc;
import com.mapperutils.entity.TbItemParamItem;
import com.rest.service.ItemInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping("/rest")
public class ItemInfoController {
	
	@Autowired
	ItemInfoService itemInfoService;

	/**
	 * 商品基本信息
	 * @param itemId
	 * @return
	 */
	@RequestMapping("/item/{itemId}")
	@ResponseBody
	public TbItem getInfo(@PathVariable Long itemId){
		return itemInfoService.getItemInfo(itemId);
	}

	/**
	 * 商品详情信息
	 * @param itemId
	 * @return
	 */
	@RequestMapping("/desc/{itemId}")
	@ResponseBody
	public TbItemDesc getDesc(@PathVariable Long itemId){
		TbItemDesc itemDesc = itemInfoService.getItemDesc(itemId);
		return itemDesc;
	}


	@RequestMapping("/param/{itemId}")
	@ResponseBody
	public TbItemParamItem getParam(@PathVariable Long itemId){
		TbItemParamItem itemParam = itemInfoService.getItemParam(itemId);
		return itemParam;
	}
}
