package com.yishuifengxiao.common.crawler.domain.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

import javax.validation.Valid;

import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.validation.annotation.Validated;

import com.yishuifengxiao.common.crawler.domain.constant.SiteConstant;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 站点信息
 * 
 * @author yishui
 * @version 1.0.0
 * @date 2019-11-5
 */
@ApiModel(value = "站点设置规则")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Accessors(chain = true)
@Validated
@Valid
public class SiteRule implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4740150684464167910L;
	/**
	 * 请求的域名，默认从请求的起始链接中提起
	 */
	@ApiModelProperty(" 请求的域名，默认从请求的起始链接中提起")
	private String domain;
	/**
	 * 浏览器标志，默认值为空，表示系统从众多内置标识符中随机选择一个
	 */
	@ApiModelProperty("浏览器标志，默认为谷歌78.0")
	private String userAgent;

	/**
	 * 用于指明当前流量的来源参考页面，默认为空，表示系统设置为当前请求的网页值
	 */
	@ApiModelProperty("用于指明当前流量的来源参考页面")
	private String referrer;

	/**
	 * 网页缓存策略，默认为 max-age=0
	 */
	@ApiModelProperty(" 网页缓存策略，默认为 max-age=0")
	private String cacheControl = SiteConstant.CACHE_CONTROL;
	/**
	 * 请求的cookie，默认为空
	 */
	@ApiModelProperty("请求的cookie，默认为空")
	private String cookies;

	/**
	 * 请求失败时重新执行此请求的次数,默认为3
	 */
	@ApiModelProperty("请求失败时重新执行此请求的次数,默认为3")
	private int retryCount = SiteConstant.RETRY_COUNT;

	/**
	 * 确定用于HTTP状态管理的cookie规范的名称<br/>
	 * 值参考 CookieSpecs ,默认为 null<br/>
	 * 
	 */
	@ApiModelProperty("确定用于HTTP状态管理的cookie规范的名称")
	private String cookieSpec;
	/**
	 * 确定是否应自动处理重定向。 <br/>
	 * <b>默认为true</b>
	 */
	@ApiModelProperty("确定是否应自动处理重定向，默认为true")
	private boolean redirectsEnabled = true;
	/**
	 * <pre>
	 * 确定是否应拒绝相对重定向。 
	 * HTTP规范要求位置值是绝对URI。
	 * <b>默认为false</b>
	 * </pre>
	 */
	@ApiModelProperty(" 确定是否应拒绝相对重定向。 默认为false")
	private boolean relativeRedirectsAllowed = false;
	/**
	 * <pre>
	 * 确定是否应允许循环重定向（重定向到同一位置）。 
	 * HTTP规范不够清楚是否允许循环重定向，因此可以选择启用它们 <br/>
	 * <b>默认为false</b>
	 * </pre>
	 */
	@ApiModelProperty("确定是否应允许循环重定向，默认为false")
	private boolean circularRedirectsAllowed = false;
	/**
	 * 返回要遵循的最大重定向数。 重定向次数的限制旨在防止无限循环 <br/>
	 * <b>默认为50</b>
	 */
	@ApiModelProperty("最大重定向数，默认为50")
	private int maxRedirects = 50;

	/**
	 * <pre>
	 * 
	 * 确定连接建立之前的超时时间（以毫秒为单位）。
	 * 
	 * 超时值零被解释为无限超时，负值被解释为未定义（如果适用，则为系统默认值）。
	 * <b>默认为-1</b>
	 * </pre>
	 */
	@ApiModelProperty("确定连接建立之前的超时时间（以毫秒为单位），默认为-1")
	private int connectTimeout = -1;

	/**
	 * <pre>
	 * 确定是否请求目标服务器压缩内容。
	 * <b>默认为true</b>
	 * </pre>
	 */
	@ApiModelProperty("是否请求目标服务器压缩内容。默认为true")
	private boolean contentCompressionEnabled = true;
	/**
	 * <pre>
	 * 确定客户端是否应规范请求中的URI。
	 *  <b>默认为true</b>
	 * </pre>
	 */
	@ApiModelProperty("确定客户端是否应规范请求中的URI。默认为true")
	private boolean normalizeUri = true;

	@ApiModelProperty("请求头信息")
	private List<HeaderRule> headers = new ArrayList<>();

	/**
	 * 获取到全部的cookie信息
	 * 
	 * @return Map<String, String> ，键为cookie的名字，值为cookie的值
	 */
	@ApiModelProperty(hidden=true)
	public Map<String, String> getCookiValues() {
		Map<String, String> map = new WeakHashMap<String, String>();
		if (StringUtils.isNotBlank(this.cookies)) {
			String[] cookieStrs = StringUtils.splitByWholeSeparatorPreserveAllTokens(this.cookies, ";");
			for (String cookieStr : cookieStrs) {
				if (StringUtils.contains(cookieStr, "=")) {
					String[] data = StringUtils.splitByWholeSeparator(cookieStr, "=");
					if (StringUtils.isNotBlank(data[0])) {
						map.put(data[0].trim(), data[1].trim());
					}
				}
			}
		}
		return map;
	}

	/**
	 * 获取到全部的请求头信息
	 * 
	 * @return Map<String, String> ，键为请求头的名字，值为请求头的值
	 */
	@ApiModelProperty(hidden=true)
	public Map<String, String> getAllHeaders() {
		Map<String, String> map = new WeakHashMap<String, String>();
		if (null != this.headers) {
			this.headers.parallelStream().filter(t -> t != null).filter(t -> StringUtils.isNotBlank(t.getHeaderName()))
					.forEach(t -> {
						map.put(t.getHeaderName(), t.getHeaderValue());
					});
		}
		if (StringUtils.isNotBlank(this.userAgent)) {
			map.put(SiteConstant.USER_AGENT, this.userAgent);
		}
		return map;
	}
   
	/**
	 * 获取浏览器标识符
	 * @return
	 */
	@ApiModelProperty(hidden=true)
	public String getAutoUserAgent() {
		if (StringUtils.isNotBlank(this.userAgent)) {
			return this.userAgent;
		}
		return SiteConstant.USER_AGENT_ARRAY[RandomUtils.nextInt(0, SiteConstant.USER_AGENT_ARRAY.length)];
	}

}
