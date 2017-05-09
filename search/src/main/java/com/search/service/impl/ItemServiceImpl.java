package com.search.service.impl;

import com.common.entity.Item;
import com.common.entity.SearchResult;
import com.common.utils.ExceptionUtil;
import com.common.utils.ResponseResult;
import com.search.dao.SearchDao;
import com.search.mapper.ItemMapper;
import com.search.service.ItemService;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional
public class ItemServiceImpl implements ItemService {
	
	@Autowired
	private ItemMapper itemMapper;
	
	@Autowired
	private SolrServer solrServer;
	
	@Autowired
	private SearchDao searchDao;

	@Override
	public ResponseResult importAllItem() throws Exception {
		try {
			List<Item> itemList = itemMapper.getItemList();
			//往solr中增加数据库中的item
			if(itemList != null && itemList.size() != 0){
				for (Item item : itemList) {
					SolrInputDocument doc = new SolrInputDocument();
					doc.addField("id", item.getId());
					doc.setField("item_title", item.getTitle());
					doc.setField("item_sell_point", item.getSell_point());
					doc.setField("item_price", item.getPrice());
					doc.setField("item_image", item.getImage());
					doc.setField("item_category_name", item.getCategory_name());
					doc.setField("item_desc", item.getItem_des());
					solrServer.add(doc);
				}
			}
			//提交
			solrServer.commit();
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseResult.build(500, ExceptionUtil.getStackTrace(e));
		}
		return ResponseResult.ok();
	}

	@Override
	public SearchResult search(String queryString, int page, int rows)
			throws Exception {
		SolrQuery solrQuery = new SolrQuery(queryString);
		solrQuery.setStart(page);
		solrQuery.setRows(rows);
		//设置默认搜素域
		solrQuery.set("df", "item_keywords");
		//设置高亮
		solrQuery.setHighlight(true);
		solrQuery.addHighlightField("item_title");
		//设置高亮前缀
		solrQuery.setHighlightSimplePre("<em style=\"color:red\">");
		//设置高亮后缀
		solrQuery.setHighlightSimplePost("</em>");
		
		//执行查询
		SearchResult result = searchDao.search(solrQuery);
		long recordCount = result.getResultCount();
		long pageCount = recordCount / rows;
		if (recordCount % rows > 0) {
			pageCount++;
		}
		result.setPageCount(pageCount);
		result.setCurPage(page);

		return result;
	}

}
