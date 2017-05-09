package com.portal.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.common.utils.JsonUtils;
import com.mapperutils.entity.TbContent;
import com.portal.feign.RestFeignService;
import com.portal.service.ContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


@Service
public class ContentServiceImpl implements ContentService {

	@Autowired
	private RestFeignService restContentService;
	
	@Override
	public String getContentList() throws Exception {
		//使用feign消费服务
		List<TbContent> list = restContentService.getContentList();
		List<Map> mapList = new ArrayList<Map>();
		for (TbContent tbContent : list) {
			Map map = new HashMap();
			map.put("src", tbContent.getPic());
			map.put("height", 240);
			map.put("width", 670);
			map.put("srcB", tbContent.getPic2());
			map.put("widthB", 550);
			map.put("heightB", 240);
			map.put("href", tbContent.getUrl());
			map.put("alt", tbContent.getSubTitle());
			mapList.add(map);
		}
		return JsonUtils.objectToJson(mapList);
	}

}
