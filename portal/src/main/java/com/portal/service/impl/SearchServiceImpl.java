package com.portal.service.impl;

import com.common.entity.SearchResult;
import com.common.utils.HttpClientUtil;
import com.common.utils.ResponseResult;
import com.portal.feign.SearchFeignService;
import com.portal.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;


@Service
public class SearchServiceImpl implements SearchService {

	@Autowired
	private SearchFeignService searchService;

	@Override
	public SearchResult search(String queryString, int page) {
		String json = searchService.query(queryString, page);
		ResponseResult result = ResponseResult.formatToPojo(json, SearchResult.class);
		SearchResult searchResult = null;
		if(result.getStatus() == 200){
			searchResult = (SearchResult) result.getData();
		}
		return searchResult;
	}

}
