package com.portal.controller;

import com.mapperutils.entity.TbItem;
import com.mapperutils.entity.TbItemDesc;
import com.portal.entity.ItemInfo;
import com.portal.service.ItemService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class ItemController {
	
	@Autowired
	ItemService itemService;

	@RequestMapping("/item/{itemId}")
	public String getItemInfo(@PathVariable Long itemId, Model model){
		TbItem item = itemService.itemInfo(itemId);
		ItemInfo itemInfo = new ItemInfo();
		BeanUtils.copyProperties(item, itemInfo);
		model.addAttribute("item", itemInfo);
		return "item";
	}
	
	@RequestMapping(value="/item/desc/{itemId}",produces=MediaType.TEXT_HTML_VALUE+";charset=utf-8")
	@ResponseBody
	public String getItemDesc(@PathVariable Long itemId){
		return itemService.showItemDesc(itemId).getItemDesc();
	}
	
	@RequestMapping(value="/item/param/{itemId}",produces=MediaType.TEXT_HTML_VALUE+";charset=utf-8")
	@ResponseBody
	public String getItemParam(@PathVariable Long itemId){
		String showItemParam = itemService.showItemParam(itemId);
		System.out.println(showItemParam);
		return showItemParam;
	}
}
