package com.search.mapper;

import com.common.entity.Item;

import java.util.List;


public interface ItemMapper {
	List<Item> getItemList() throws Exception;
}
