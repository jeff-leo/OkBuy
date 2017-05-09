package com.portal.service;


import com.common.entity.SearchResult;

public interface SearchService {
	SearchResult search(String queryString, int page);
}
