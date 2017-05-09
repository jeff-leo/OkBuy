package com.portal.feign;

import com.mapperutils.entity.TbItem;
import com.mapperutils.entity.TbItemDesc;
import com.mapperutils.entity.TbItemParamItem;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Administrator on 2017/5/8.
 */
@FeignClient("rest")
public interface ItemFeignService {

    @RequestMapping(value = "${REST_ITEM_INFO}/{itemId}")
    TbItem getItemInfo(@PathVariable("itemId") Long itemId);

    @RequestMapping(value = "${REST_ITEM_DESC}/{itemId}")
    TbItemDesc getItemDesc(@PathVariable("itemId") Long itemId);

    @RequestMapping(value = "${REST_ITEM_PARAM}/{itemId}")
    TbItemParamItem getItemParam(@PathVariable("itemId") Long itemId);
}
