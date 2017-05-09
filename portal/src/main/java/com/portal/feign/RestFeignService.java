package com.portal.feign;

import com.common.utils.ResponseResult;
import com.mapperutils.entity.TbContent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * Created by Administrator on 2017/5/7.
 */
@FeignClient(name = "rest")
public interface RestFeignService {

    @RequestMapping(value = "${REST_AD_INDEX_URL}", method = RequestMethod.GET)
    List<TbContent> getContentList();

}
