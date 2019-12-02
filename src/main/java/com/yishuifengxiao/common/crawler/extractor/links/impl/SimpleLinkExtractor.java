package com.yishuifengxiao.common.crawler.extractor.links.impl;

import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import com.yishuifengxiao.common.crawler.extractor.links.LinkExtractor;

/**
 * 简单实现的链接提取器
 * 
 * @author yishui
 * @version 1.0.0
 * @date 2019-11-5
 */
public class SimpleLinkExtractor implements LinkExtractor {

	private String regex;

	@Override
	public List<String> extract(List<String> links) {
		// 放入下一步的链接
		links = links.parallelStream().filter(t -> Pattern.matches(regex, t)).collect(Collectors.toList());
		return links;
	}

	public SimpleLinkExtractor() {
	}

	public SimpleLinkExtractor(String regex) {
		this.regex = regex;
	}

	public String getRegex() {
		return regex;
	}

	public void setRegex(String regex) {
		this.regex = regex;
	}
}
