package com.rest.po;

import java.util.List;

/**
 * 返回给jsonp
 * @author Administrator
 *
 */
public class CatResult {
	private List<?> data;

	public List<?> getData() {
		return data;
	}

	public void setData(List<?> data) {
		this.data = data;
	}
}
