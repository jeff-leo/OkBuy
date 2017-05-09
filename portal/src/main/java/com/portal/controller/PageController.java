package com.portal.controller;

import java.io.UnsupportedEncodingException;

import com.common.entity.SearchResult;
import com.portal.service.ContentService;
import com.portal.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class PageController {

	@Autowired
	private ContentService contentService;

	@RequestMapping("/")
	public String showIndex(Model model) throws Exception {
		String contentList = contentService.getContentList();
		model.addAttribute("ad1", contentList);
		return "index";
	}

}
