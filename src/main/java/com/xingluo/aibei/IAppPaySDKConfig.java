package com.xingluo.aibei;


public class IAppPaySDKConfig{

	/**
	 * 应用名称：
	 * 应用在iAppPay云支付平台注册的名称
	 */
	public final static  String APP_NAME = "一圆梦";

	/**
	 * 应用编号：
	 * 应用在iAppPay云支付平台的编号，此编号用于应用与iAppPay云支付平台的sdk集成
	 */
	public final static  String APP_ID = "3003964714";

	/**
	 * 商品编号：
	 * 应用的商品在iAppPay云支付平台的编号，此编号用于iAppPay云支付平台的sdk到iAppPay云支付平台查找商品详细信息（商品名称、商品销售方式、商品价格）
	 * 编号对应商品名称为：抢币
	 */
	public final static  int WARES_ID_1=1;

	/**
	 * 应用私钥：
	 * 用于对商户应用发送到平台的数据进行加密
	 */
	public final static String APPV_KEY = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAILqLlpF7IyrRAqVqo2DVL5s8x4O4c/q/1JZ3wqELIQsCAP0LUZiR32xaLLB88AZWEE0bE7N/GkxDl69Evrx4vqm/0NIOHjd6T2z9XgpBSaa2eEO2VcuuZBP7pczp7xHDwxQEUYCwHgW1fyheqLQUsK3IqBlMlrGIaYy5fFvqjrZAgMBAAECgYAjjodYbXV6H2ozlcLk/K6fqee+WjxqxGDkdRRnPs74dx0WNdO2MitIsE7HfnY9zqRx0VM5dCkCSRimEqR9FAWZunL89RA5I/h3C6cchX2g8jOuyrbypKRjkDzt32p5RjYhCviqPnTBi61oKK7pmWu0O7zM8adFcWyRCqWt1j/roQJBAM2taQ++y0yzFtniIY7S6zITP2ZmpBCA65L/tlKIHwiQ4GsrvxbmKobm488PNTL0OksTAP2dRoIfkPiqeAQP8n8CQQCi8gTn3Il+jKFxSS6clNuR5kH0TXWgXR0FLmqnebF78QIc54Xl/8+vPous7LfvrFg4uylhFB0648mlmR+BQ/anAkAfNSiz0udWA/Z7qOwel7JILtN33isWtfkInPQ1IaJT0me62zBrcRES1wXJ7SzNRnFx4rhLwwlnxRJiKLt9Ik5RAkA31w6dUrN/vbvZscWafVzdm/YAPcdQpHg8fVKpgAe1vLsHosP9NPQ8TwfYw+qo5Pv6vREKJrV5p/RDnrzwNJVDAkEAg6Og7IRj2Vivkgt1C7NseBUaIFm/SbFH9UKEfmmekF3jx32hm2uTjFWdsq6IzWuCKXw24vz+KU1I3hyOGz2Wgw==";

	/**
	 * 平台公钥：
	 * 用于商户应用对接收平台的数据进行解密

	 */
	public final static String PLATP_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC3V1ukOS0jrLf+9ji1UTmxITwhQyfbM3tIja874JBRPvO3UUZ95bRbjrydHts5oixEr9Sdiz8W3nka1OPJBEDk9XikgVs2Sa71GTdQgz+UWjt8BDDlqLrzL6QxlVkw0zZJN8nm58cmHQtgP0GM01L/6pPhsCEBDyk5ONUkSAYMtwIDAQAB";

}