package com.rest.service.impl;

import java.util.List;

import com.common.utils.JsonUtils;
import com.common.utils.ResponseResult;
import com.mapperutils.entity.TbItem;
import com.mapperutils.entity.TbItemDesc;
import com.mapperutils.entity.TbItemParamItem;
import com.mapperutils.entity.TbItemParamItemExample;
import com.mapperutils.mapper.TbItemDescMapper;
import com.mapperutils.mapper.TbItemMapper;
import com.mapperutils.mapper.TbItemParamItemMapper;
import com.rest.dao.JedisClient;
import com.rest.service.ItemInfoService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ItemInfoServiceImpl implements ItemInfoService {

	@Autowired
	TbItemMapper itemMapper;
	@Autowired
	TbItemDescMapper itemDescMapper;
	@Autowired
	TbItemParamItemMapper itemParamItemMapper;

	@Autowired
	JedisClient jedisClient;

	@Value("${REDIS_ITEM_KEY}")
	private String REDIS_ITEM_KEY;
	@Value("${REDIS_ITEM_EXPIRE}")
	private Integer REDIS_ITEM_EXPIRE;

	/**
	 * 获取商品基本信息
	 * @param itemId
	 * @return
	 */
	@Override
	public TbItem getItemInfo(Long itemId) {

		// 先从读取缓存， ：可以用于Redis分类显示
		try {
			String string = jedisClient.get(REDIS_ITEM_KEY + ":" + itemId + ":base");
			if (!StringUtils.isBlank(string)) {
				TbItem item = JsonUtils.jsonToPojo(string, TbItem.class);
				// 如果缓存中有，则直接缓存
				//return ResponseResult.ok(item);
				return item;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		TbItem item = itemMapper.selectByPrimaryKey(itemId);
		// 缓存处理
		try {
			jedisClient.set(REDIS_ITEM_KEY + ":" + itemId + ":base",
					JsonUtils.objectToJson(item));
			jedisClient.expire(REDIS_ITEM_KEY + ":" + itemId + ":base",
					REDIS_ITEM_EXPIRE);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return item;
	}

	/**
	 * 获取商品详情
	 * @param itemId
	 * @return
	 */
	@Override
	public TbItemDesc getItemDesc(Long itemId) {
		// 读取缓存
		try {
			String string = jedisClient.get(REDIS_ITEM_KEY + ":" + itemId + ":desc");
			if (!StringUtils.isBlank(string)) {
				TbItemDesc itemDesc = JsonUtils.jsonToPojo(string,
						TbItemDesc.class);
				// 如果缓存中有，则直接缓存
				return itemDesc;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		TbItemDesc itemDesc = itemDescMapper.selectByPrimaryKey(itemId);
		// 缓存处理
		try {
			jedisClient.set(REDIS_ITEM_KEY + ":" + itemId + ":desc",
					JsonUtils.objectToJson(itemDesc));
			jedisClient.expire(REDIS_ITEM_KEY + ":" + itemId + ":desc",
					REDIS_ITEM_EXPIRE);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return itemDesc;
	}

	/**
	 * 获取商品规格参数
	 * @param itemId
	 * @return
	 */
	@Override
	public TbItemParamItem getItemParam(Long itemId) {
		// 读取缓存
		try {
			String string = jedisClient.get(REDIS_ITEM_KEY + ":" + itemId + ":param");
			if (!StringUtils.isBlank(string)) {
				TbItemParamItem paramItem = JsonUtils.jsonToPojo(string,
						TbItemParamItem.class);
				// 如果缓存中有，则直接缓存
				return paramItem;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		TbItemParamItemExample example = new TbItemParamItemExample();
		TbItemParamItemExample.Criteria criteria = example.createCriteria();
		criteria.andItemIdEqualTo(itemId);
		List<TbItemParamItem> paramItemList = itemParamItemMapper
				.selectByExampleWithBLOBs(example);
		if (paramItemList != null && paramItemList.size() != 0) {
			TbItemParamItem paramItem = paramItemList.get(0);
			try {
				jedisClient.set(REDIS_ITEM_KEY + ":" + itemId + ":param",
						JsonUtils.objectToJson(paramItem));
				jedisClient.expire(REDIS_ITEM_KEY + ":" + itemId + ":param",
						REDIS_ITEM_EXPIRE);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return paramItem;
		}
		
		return null;
	}

}
