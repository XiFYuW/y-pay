package com.y.pay.wx.tool;

import java.io.*;

public class WxConfig extends WXPayConfig {

	// appId
	private String appId;
	// 商户id
	private String mchId;
	// 商户密钥
	private String key;
	// 证书
	private String certPath;

	private byte[] certData;

	public WxConfig(String appId, String mchId, String key, String certPath) throws IOException {
		super();
		this.appId = appId;
		this.mchId = mchId;
		this.key = key;
		if (certPath != null && certPath.length() > 0) {
			this.certPath = certPath;
			InputStream certStream = null;
			try {
				File file = new File(certPath);
				certStream = new FileInputStream(file);
				this.certData = new byte[(int) file.length()];
				certStream.read(this.certData);
			}catch (Exception ignored) {

			}finally {
				if (certStream != null) {
					certStream.close();
				}
			}
		}
	}

	public String getCertPath() {
		return certPath;
	}

	/**
	 * 获取 App ID
	 *
	 * @return App ID
	 */
	@Override
	public String getAppID() {
		return appId;
	}

	/**
	 * 获取 Mch ID
	 *
	 * @return Mch ID
	 */
	@Override
	public String getMchID() {
		return mchId;
	}

	/**
	 * 获取 API 密钥
	 *
	 * @return API密钥
	 */
	@Override
	public String getKey() {
		return key;
	}

	/**
	 * 获取商户证书内容
	 *
	 * @return 商户证书内容
	 */
	@Override
	public InputStream getCertStream() {
		return new ByteArrayInputStream(this.certData);
	}

	/**
	 * HTTP(S) 连接超时时间，单位毫秒
	 */
	public int getHttpConnectTimeoutMs() {
		return 8000;
	}

	/**
	 * HTTP(S) 读数据超时时间，单位毫秒
	 */
	public int getHttpReadTimeoutMs() {
		return 10000;
	}

	/**
	 * 获取WXPayDomain, 用于多域名容灾自动切换
	 */
	@Override
	public IWXPayDomain getWXPayDomain() {
		return new IWXPayDomain() {
			@Override
			public void report(String domain, long elapsedTimeMillis, Exception ex) {

			}
			@Override
			public DomainInfo getDomain(WXPayConfig config) {
				return new DomainInfo(WXPayConstants.DOMAIN_API, true);
			}
		};
	}

}
