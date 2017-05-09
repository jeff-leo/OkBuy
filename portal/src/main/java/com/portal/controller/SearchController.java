package com.portal.controller;

import com.common.entity.SearchResult;
import com.portal.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.UnsupportedEncodingException;

/**
 * Created by Administrator on 2017/5/8.
 */
@Controller
public class SearchController {

    @Autowired
    private SearchService searchService;

    @RequestMapping("/search")
    public String searchPage(@RequestParam("q") String queryString,
                             @RequestParam(defaultValue = "1") int page, Model model)
            throws Exception {
        if (queryString != null) {
            try {
                queryString = new String(queryString.getBytes("iso8859-1"),
                        "utf-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        SearchResult searchResult = searchService.search(queryString, page);
        // 向页面传递参数
        model.addAttribute("query", queryString);
        model.addAttribute("totalPages", searchResult.getPageCount());
        model.addAttribute("itemList", searchResult.getItemList());
        model.addAttribute("page", page);

        return "search";

    }
}
