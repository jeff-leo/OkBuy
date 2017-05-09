package com.portal.service;


import com.mapperutils.entity.TbItem;
import com.mapperutils.entity.TbItemDesc;
import com.portal.entity.ItemInfo;

public interface ItemService {
	TbItem itemInfo(Long itemId);
	TbItemDesc showItemDesc(Long itemId);
	String showItemParam(Long itemId);
}
