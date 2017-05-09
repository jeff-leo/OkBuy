package com.rest.service;


import com.common.utils.ResponseResult;
import com.mapperutils.entity.TbItem;
import com.mapperutils.entity.TbItemDesc;
import com.mapperutils.entity.TbItemParamItem;

public interface ItemInfoService {
	TbItem getItemInfo(Long itemId);
	TbItemDesc getItemDesc(Long itemId);
	TbItemParamItem getItemParam(Long itemId);
}
