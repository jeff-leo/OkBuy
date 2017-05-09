package com.search.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.common.entity.Item;
import com.common.entity.SearchResult;
import com.search.dao.SearchDao;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;



@Repository
public class SearchDaoImpl implements SearchDao {
	
	@Autowired
	private SolrServer solrServer;

	@Override
	public SearchResult search(SolrQuery query) {
		SearchResult result = new SearchResult();
		List<Item> itemList = null;
		try {
			QueryResponse response = solrServer.query(query);
			//取出查询结果
			SolrDocumentList docList = response.getResults();
			//取查询结果总数量
			result.setResultCount(docList.getNumFound());
			//取高亮总数
			Map<String, Map<String, List<String>>> highlighting = response.getHighlighting();
			itemList = new ArrayList<Item>();
			for(SolrDocument doc : docList){
				Item item = new Item();
				String id = (String) doc.get("id");
				item.setId(id);
				//取出id对应的高亮标题
				List<String> list = highlighting.get(id).get("item_title");
				String title = "";
				if(list != null && list.size() != 0){
					title = list.get(0);
				}else{//list为空表示标题没有高亮
					title = (String) doc.get("title");
				}
				item.setTitle(title);
				item.setImage((String) doc.get("item_image"));
				item.setPrice((long) doc.get("item_price"));
				item.setSell_point((String) doc.get("item_sell_point"));
				item.setCategory_name((String) doc.get("item_category_name"));
				itemList.add(item);
			}
			
		} catch (SolrServerException e) {
			e.printStackTrace();
		}
		result.setItemList(itemList);
		return result;
	}

}
