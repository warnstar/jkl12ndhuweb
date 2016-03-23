package com.xingluo.aibei;

/**
 *应用接入iAppPay云支付平台sdk集成信息 
 */
public class IAppPaySDKConfi_ForAndriod{

	/**
	 * 应用名称：
	 * 应用在iAppPay云支付平台注册的名称
	 */
	public final static  String APP_NAME = "一圆梦";

	/**
	 * 应用编号：
	 * 应用在iAppPay云支付平台的编号，此编号用于应用与iAppPay云支付平台的sdk集成 
	 */
	public final static  String APP_ID = "3003969474";

	/**
	 * 商品编号：
	 * 应用的商品在iAppPay云支付平台的编号，此编号用于iAppPay云支付平台的sdk到iAppPay云支付平台查找商品详细信息（商品名称、商品销售方式、商品价格）
	 * 编号对应商品名称为：充值
	 */
	public final static  int WARES_ID_1=1;

	/**
	 * 应用私钥：
	 * 用于对商户应用发送到平台的数据进行加密
	 */
	public final static String APPV_KEY = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBALl/LrN2TndPybypIJJbwWNSJq1wKB1ZwVDJfO/Xi1gs561baBOFD+vsTp6gOiDEz2axOSwi2LIVppAmPDu+xAYgolCpXpMLJZLjH9MoFExqMfRfPYRqU7MUhZg0fB6nai/ZPAkzw6bH3ufQJ5WyychWhL8rJLQYt5zHk4/gGidJAgMBAAECgYB1Pct4plfl1Uo+se7iUpgifHPxvye7lVU4CIGs3eE2s/sieNmJLNrpVIxEeiUDPSGRWIY4PTYVKNYD2gbMBEDEQjYYFTLGIAIJThlwyW+ahqwwFFZgeEpVEKm7XBM9PJUb0XcDVsFRtBbmPczTrEqTHzrQ6Lig3wcwlBr2WvbVGQJBAO1jnQwwSD6BKsqr0ChRPH5r18eFyxJJ1RtIwMU0obZt5Ie9715o1Z0HXoc7CiX0dSQvEJ3li9pskifhGbXfXJcCQQDIChfZrjp3WmuhOxLKXKFamCzUydobkMyWpQ2vvGk6MfLy8PzP7KT1C4KuE47xP8cnBm/ywTFqwPbAmpP90rcfAkBoC2Tl1rOTUaGjurI9OX2+Gx40ANwEY//XOYfj5zGJd5jWNdUYF/KoJHa15paIFCDTfx1VVyp4zWUxvWM7Z255AkEAsm6eW6NXr4LOIqEo36zEmKTIyH3MuW3tsCpGDQ2I50oKpb1kJKu3cIT16gS0KmlzYVISpXzCcbDx6Vd4zp4YjQJAcU2ujJDV4yekfHpJIOo/XBissa5esdxsqrxYPbd11vxDOhI5iZgYCpfJ+GBY7+LUklbtlcDoFq0kESLbSaWBYg==";

	/**
	 * 平台公钥：
	 * 用于商户应用对接收平台的数据进行解密
	
	 */
	public final static String PLATP_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCHOMoNLvRQ5nGA4XcvCZFMUM7GPa8r+HLxkrX+2y3sqEE7psL45EpYf/vUQD4hQvmZ5IR0GA0/4vjPl9KVEZZ/1uhiUXezS/pdhbkRli26J3sZCVXGoW58zeortUdSnVskY+Zoj8JOMXoPAHGr+Htp8bo8viM6nC4nEx1njGxYZwIDAQAB";

}