package com.rest.service.impl;

import java.util.List;

import com.mapperutils.entity.TbContent;
import com.mapperutils.entity.TbContentExample;
import com.mapperutils.mapper.TbContentMapper;
import com.rest.service.ContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service
public class ContentServiceImpl implements ContentService {
	
	@Autowired
	private TbContentMapper contentMapper;

	@Override
	public List<TbContent> getContentList(long categoryId) throws Exception {
		TbContentExample example = new TbContentExample();
		TbContentExample.Criteria criteria = example.createCriteria();
		criteria.andCategoryIdEqualTo(categoryId);
		List<TbContent> list = contentMapper.selectByExample(example);
		return list;
	}

}
