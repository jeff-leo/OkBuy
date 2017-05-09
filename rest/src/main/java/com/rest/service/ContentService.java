package com.rest.service;

import com.mapperutils.entity.TbContent;

import java.util.List;


public interface ContentService {
	List<TbContent> getContentList(long categoryId) throws Exception;
}
