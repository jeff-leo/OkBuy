package com.search.dao;

import com.common.entity.SearchResult;
import org.apache.solr.client.solrj.SolrQuery;



public interface SearchDao {
	public SearchResult search(SolrQuery query);
}
