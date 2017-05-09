package com.rest.service;

import com.rest.po.CatResult;

import java.util.List;


public interface ItemCatService {
	public CatResult getCatList() throws Exception;
	public List<?> getCatItem(long parentId) throws Exception;
}
