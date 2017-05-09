package com.portal.feign;

import com.common.entity.SearchResult;
import com.common.utils.ResponseResult;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by Administrator on 2017/5/7.
 */
@FeignClient(name = "search")
public interface SearchFeignService {

    @RequestMapping(value = "${SEARCH_URL}", method = RequestMethod.GET)
    String query(@RequestParam("q") String param);

    @RequestMapping(value = "${SEARCH_URL}", method = RequestMethod.GET)
    String query(@RequestParam("q") String param,
                         @RequestParam("1") int page);

    @RequestMapping(value = "${SEARCH_URL}", method = RequestMethod.GET)
    String query(@RequestParam("q") String param,
                       @RequestParam("1") int page,
                       @RequestParam("60") int rows);
}
