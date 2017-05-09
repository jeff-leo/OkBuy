package com.rest.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.mapperutils.entity.TbItemCat;
import com.mapperutils.entity.TbItemCatExample;
import com.mapperutils.mapper.TbItemCatMapper;
import com.rest.po.CatNode;
import com.rest.po.CatResult;
import com.rest.service.ItemCatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service
public class ItemCatServiceImpl implements ItemCatService {

	@Autowired
	private TbItemCatMapper itemCatMaper;
	
	@Override
	public CatResult getCatList() throws Exception {
		CatResult result = new CatResult();
		List<?> data = getCatItem(0);
		result.setData(data);
		return result;
	}
	
	@Override
	public List<?> getCatItem(long parentId) throws Exception {
		TbItemCatExample example = new TbItemCatExample();
		TbItemCatExample.Criteria criteria = example.createCriteria();
		criteria.andParentIdEqualTo(parentId);
		List<TbItemCat> itemCatList = itemCatMaper.selectByExample(example);
		List resultList = new ArrayList();
		if(itemCatList != null && itemCatList.size() != 0){
			for (TbItemCat tbItemCat : itemCatList) {
				//如果是父节点
				if(tbItemCat.getIsParent()){
					CatNode catNode = new CatNode();
					if(parentId == 0){
						catNode.setName("<a href='/products/"+tbItemCat.getId()+".html'>"+
					tbItemCat.getName()+"</a>");
					}else{
						catNode.setName(tbItemCat.getName());
					}
					catNode.setUrl("/products/"+tbItemCat.getId()+".html");
					catNode.setItem(getCatItem(tbItemCat.getId()));
					
					resultList.add(catNode);
				}else{
					resultList.add("/products/"+tbItemCat.getId()+".html|" + tbItemCat.getName());
				}
			}
		}
		return resultList;
	}

}
