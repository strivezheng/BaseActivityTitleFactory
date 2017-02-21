package com.xunku.base.nohttp;

import com.yolanda.nohttp.rest.Request;

/**
 * 上拉加载时候取得数据的handle
 * 董晓飞 2016/06/30
 * */
public class MyRequestHandle implements RequestHandle {
	private Request<String> request;

	public MyRequestHandle(Request<String> request) {
		super();
		this.request = request;
	}

	@Override
	public void cancle() {
		request.cancel();
	}

	@Override
	public boolean isRunning() {
		return false;
	}
}