package com.yishuifengxiao.common.crawler.downloader;

import com.yishuifengxiao.common.crawler.domain.entity.Page;

/**
 * 网页下载器
 * @author yishui
 * @date 2019年11月20日
 * @version 1.0.0
 */
public interface Downloader {
	/**
	 * 下载对应的请求资源
	 * @param url 需要下载的网页的地址
	 * @return
	 */
	Page down(final String url);
}
