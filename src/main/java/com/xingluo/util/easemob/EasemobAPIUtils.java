package com.xingluo.util.easemob;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.xingluo.util.easemob.vo.*;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;

public class EasemobAPIUtils {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(EasemobAPIUtils.class);
	private static final JsonNodeFactory factory = new JsonNodeFactory(false);

	// 通过app的client_id和client_secret来获取app管理员token
	private static Credential credential = new ClientSecretCredential(
			Constants.APP_CLIENT_ID, Constants.APP_CLIENT_SECRET,
			ClientSecretCredential.USER_ROLE_APPADMIN);
	
	/**
     * 注册IM用户[单个]
     */
	public static boolean register(String username){
        ObjectNode datanode = JsonNodeFactory.instance.objectNode();
        datanode.put("username",username);
        datanode.put("password", Constants.DEFAULT_PASSWORD);
        ObjectNode createNewIMUserSingleNode = createNewIMUserSingle(datanode);
        if (null != createNewIMUserSingleNode) {
            LOGGER.info("注册IM用户[单个]: " + createNewIMUserSingleNode.toString());
            return true;
        }
        return false;
	}
	/**
	 * 删除IM用户[单个]
	 *
	 * 删除指定Constants.APPKEY下IM单个用户
	 *
	 *
	 * @param userName
	 * @return
	 */
	public static ObjectNode deleteIMUserByuserName(String userName) {
		ObjectNode objectNode = factory.objectNode();

		// check Constants.APPKEY format
		if (!HTTPClientUtils.match("^(?!-)[0-9a-zA-Z\\-]+#[0-9a-zA-Z]+", Constants.APPKEY)) {
			LOGGER.error("Bad format of Constants.APPKEY: " + Constants.APPKEY);

			objectNode.put("message", "Bad format of Constants.APPKEY");

			return objectNode;
		}

		try {

			URL deleteUserPrimaryUrl = HTTPClientUtils.getURL(Constants.APPKEY.replace("#", "/") + "/users/"
					+ userName);
			objectNode = HTTPClientUtils.sendHTTPRequest(deleteUserPrimaryUrl, credential, null,
					HTTPMethod.METHOD_DELETE);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return objectNode;
	}
	/**
	 * 注册IM用户[单个]
	 * 
	 * 给指定Constants.APPKEY创建一个新的用户
	 * 
	 * @param dataNode
	 * @return
	 */
	private static ObjectNode createNewIMUserSingle(ObjectNode dataNode) {
		ObjectNode objectNode = factory.objectNode();
		// check Constants.APPKEY format
		if (!HTTPClientUtils.match("^(?!-)[0-9a-zA-Z\\-]+#[0-9a-zA-Z]+", Constants.APPKEY)) {
			LOGGER.error("Bad format of Constants.APPKEY: " + Constants.APPKEY);
			objectNode.put("message", "Bad format of Constants.APPKEY");
			return objectNode;
		}
		objectNode.removeAll();
		// check properties that must be provided
		if (null != dataNode && !dataNode.has("username")) {
			LOGGER.error("Property that named username must be provided .");
			objectNode.put("message", "Property that named username must be provided .");
			return objectNode;
		}
		if (null != dataNode && !dataNode.has("password")) {
			LOGGER.error("Property that named password must be provided .");
			objectNode.put("message", "Property that named password must be provided .");
			return objectNode;
		}
		try {
			objectNode = HTTPClientUtils.sendHTTPRequest(EndPoints.USERS_URL, credential, dataNode, HTTPMethod.METHOD_POST);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return objectNode;
	}
	
	
	/**
	 * IM用户登录
	 * 
	 * @param username
	 * @param password
	 * @return
	 */
	public static ObjectNode login(String username, String password) {

		ObjectNode objectNode = factory.objectNode();

		// check appKey format
		if (!HTTPClientUtils.match("^(?!-)[0-9a-zA-Z\\-]+#[0-9a-zA-Z]+", Constants.APPKEY)) {
			LOGGER.error("Bad format of Appkey: " + Constants.APPKEY);

			objectNode.put("message", "Bad format of Appkey");

			return objectNode;
		}
		if (StringUtils.isEmpty(username)) {
			LOGGER.error("Your username must be provided，the value is username of imuser.");

			objectNode.put("message", "Your username must be provided，the value is username of imuser.");

			return objectNode;
		}
		if (StringUtils.isEmpty(password)) {
			LOGGER.error("Your password must be provided，the value is username of imuser.");

			objectNode.put("message", "Your password must be provided，the value is username of imuser.");

			return objectNode;
		}

		try {
			ObjectNode dataNode = factory.objectNode();
			dataNode.put("grant_type", "password");
			dataNode.put("username", username);
			dataNode.put("password", password);

			objectNode = HTTPClientUtils.sendHTTPRequest(EndPoints.TOKEN_APP_URL, null, dataNode,
					HTTPMethod.METHOD_POST);

		} catch (Exception e) {
			throw new RuntimeException("Some errors occurred while fetching a token by username and password .");
		}

		return objectNode;
	}
	
	/**
	 * 检测用户是否在线
	 * 
	 * @param username
     *
	 * @return
	 */
	public static ObjectNode getUserStatus(String username) {
		ObjectNode objectNode = factory.objectNode();

		// check appKey format
		if (!HTTPClientUtils.match("^(?!-)[0-9a-zA-Z\\-]+#[0-9a-zA-Z]+", Constants.APPKEY)) {
			LOGGER.error("Bad format of Constants.APPKEY: " + Constants.APPKEY);
			objectNode.put("message", "Bad format of Constants.APPKEY");
			return objectNode;
		}

		// check properties that must be provided
		if (StringUtils.isEmpty(username)) {
			LOGGER.error("You must provided a targetUserName .");
			objectNode.put("message", "You must provided a targetUserName .");
			return objectNode;
		}

		try {
			URL userStatusUrl = HTTPClientUtils.getURL(Constants.APPKEY.replace("#", "/") + "/users/"
					+ username + "/status");
			objectNode = HTTPClientUtils.sendHTTPRequest(userStatusUrl, credential, null, HTTPMethod.METHOD_GET);
			String userStatus = objectNode.get("data").path(username).asText();
			if ("online".equals(userStatus)) {
				LOGGER.error(String.format("The status of user[%s] is : [%s] .", username, userStatus));
			} else if ("offline".equals(userStatus)) {
				LOGGER.error(String.format("The status of user[%s] is : [%s] .", username, userStatus));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return objectNode;
	}

	/**
	 * 发送消息
	 * 
	 * @param targetType
	 *            消息投递者类型：users 用户, chatgroups 群组
	 * @param target
	 *            接收者ID 必须是数组,数组元素为用户ID或者群组ID
	 * @param msg
	 *            消息内容
	 * @param from
	 *            发送者
	 * @param ext
	 *            扩展字段
	 * 
	 * @return 请求响应
	 */
	public static ObjectNode sendMessages(String targetType, ArrayNode target, ObjectNode msg, String from,
			ObjectNode ext) {

		ObjectNode objectNode = factory.objectNode();

		ObjectNode dataNode = factory.objectNode();

		// check Constants.APPKEY format
		if (!HTTPClientUtils.match("^(?!-)[0-9a-zA-Z\\-]+#[0-9a-zA-Z]+", Constants.APPKEY)) {
			LOGGER.error("Bad format of Constants.APPKEY: " + Constants.APPKEY);

			objectNode.put("message", "Bad format of  APPKEY");

			return objectNode;
		}

		// check properties that must be provided
		if (!("users".equals(targetType) || "chatgroups".equals(targetType))) {
			LOGGER.error("TargetType must be users or chatgroups .");

			objectNode.put("message", "TargetType must be users or chatgroups .");

			return objectNode;
		}

		try {
			// 构造消息体
			dataNode.put("target_type", targetType);
			dataNode.put("target", target);
			dataNode.put("msg", msg);
			dataNode.put("from", from);
			dataNode.put("ext", ext);

			objectNode = HTTPClientUtils.sendHTTPRequest(EndPoints.MESSAGES_URL, credential, dataNode,
					HTTPMethod.METHOD_POST);

			objectNode = (ObjectNode) objectNode.get("data");
			for (int i = 0; i < target.size(); i++) {
				String resultStr = objectNode.path(target.path(i).asText()).asText();
				if ("success".equals(resultStr)) {
					LOGGER.error(String.format("Message has been send to user[%s] successfully .", target.path(i)
							.asText()));
				} else if (!"success".equals(resultStr)) {
					LOGGER.error(String.format("Message has been send to user[%s] failed .", target.path(i).asText()));
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return objectNode;
	}

	
}
