package com.xingluo.util;

import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;

/**
 * 封装一些七牛的通用操作，增加图片，删除图片
 * @author fy
 *
 */
public class QiniuUtil {
	/**
	 * 用来获得七牛的uptoken值
	 * 传人 空间名称 和 需要生成的 key
	 * 方宇
	 */
	public String getUpToken(String bucketname,String key ){
		// 配置密匙
		/*
		 * 该ak和sk为陌途用户
		 * 需要替换为自己工程的ak和sk
		 */
		Auth auth = Auth.create("tXi4kuZdvp1zjEKDXDatmnEK_xcY68qofVUVqLYs",
				"Ol8XUVLGsgx8OFPua54KcYuR1zypTgz2tm3s2NXF");
		/**
		* 生成上传token
		*
		* @param bucket  空间名
		* @param key     key，可为 null
		* @param expires 有效时长，单位秒。默认3600s
		* @param policy  上传策略的其它参数，如 new StringMap().put("endUser", "uid").putNotEmpty("returnBody", "")。
		*        scope通过 bucket、key间接设置，deadline 通过 expires 间接设置
		* @param strict  是否去除非限定的策略字段，默认true
		* @return 生成的上传token
		*/
		String upToken =  auth.uploadToken(bucketname, null, 3600, new StringMap()
        .putNotEmpty("saveKey", key), true);
		
		return upToken;
	}
	
}
