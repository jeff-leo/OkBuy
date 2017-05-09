package com.search.service;


import com.common.entity.SearchResult;
import com.common.utils.ResponseResult;

public interface ItemService {
	ResponseResult importAllItem() throws Exception;
	SearchResult search(String queryString, int page, int rows) throws Exception;
}
