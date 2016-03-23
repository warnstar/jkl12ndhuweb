package com.xingluo.util;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;

/**
 * Utils Xml 2 Json 
 * 
 * @author Mounate Yan。
 */
public class Xml2JsonUtils {
	private static final Log log = LogFactory.getLog(Xml2JsonUtils.class);

	/**
	 * 转换一个xml格式的字符串到json格式
	 * 
	 * @param xml
	 *            xml格式的字符串
	 * @return 成功返回json 格式的字符串;失败反回null
	 */
	public static String xml2JSON(String xml) {
		JSONObject obj = new JSONObject();
		try {
			InputStream is = new ByteArrayInputStream(xml.getBytes("utf-8"));
			SAXBuilder sb = new SAXBuilder();
			Document doc = sb.build(is);
			Element root = doc.getRootElement();
			obj.put(root.getName(), iterateElement(root));
			return obj.toString();
		} catch (Exception e) {
			log.error("传入XML后转换JSON出现错误===== Xml2JsonUtil-->xml2JSON============>>",e);
			return null;
		}
	}

	public static String xml2JSON2(String xml) {
		JSONObject obj = new JSONObject();
		try {
			InputStream is = new ByteArrayInputStream(xml.getBytes("utf-8"));
			SAXBuilder sb = new SAXBuilder();
			Document doc = sb.build(is);
			Element root = doc.getRootElement();
			obj.put(root.getName(), iterateElement2(root));
			return obj.toString();
		} catch (Exception e) {
			log.error("传入XML后转换JSON出现错误===== Xml2JsonUtil-->xml2JSON============>>",e);
			return null;
		}
	}

	/**
	 * 转换一个xml格式的字符串到json格式
	 * 
	 * @param file
	 *            java.io.File实例是一个有效的xml文件
	 * @return 成功反回json 格式的字符串;失败反回null
	 */
	public static String xml2JSON(File file) {
		JSONObject obj = new JSONObject();
		try {
			SAXBuilder sb = new SAXBuilder();
			Document doc = sb.build(file);
			Element root = doc.getRootElement();
			obj.put(root.getName(), iterateElement(root));
			return obj.toString();
		} catch (Exception e) {
			log.error("传入文件后转换JSON出现错误====Xml2JsonUtil-->xml2JSON=============>>",	e);
			return null;
		}
	}
	
	/**
	 * 转换一个xml格式的字符串到json格式
	 * 
	 * @param file
	 *            java.io.File实例是一个有效的xml文件
	 * @return 成功反回json 格式的字符串;失败反回null
	 */
	public static JSONObject xml2JSONObject(File file) {
		JSONObject obj = new JSONObject();
		try {
			SAXBuilder sb = new SAXBuilder();
			Document doc = sb.build(file);
			Element root = doc.getRootElement();
			obj.put(root.getName(), iterateElement2(root));
			return obj;
		} catch (Exception e) {
			log.error("传入文件后转换JSON出现错误====Xml2JsonUtil-->xml2JSON=============>>",	e);
			return null;
		}
	}
	
	/**
     * 一个迭代方法
     * 
     * @param element
     *            : org.jdom.Element
     * @return java.util.Map 实例
     */
     @SuppressWarnings("unchecked")
     private static Map  iterateElement2(Element element) {
          List jiedian = element.getChildren();
          Element et = null;
          Map obj = new HashMap();
          List list = null;
          for (int i = 0; i < jiedian.size(); i++) {
               list = new LinkedList();
               et = (Element) jiedian.get(i);
               if (et.getTextTrim().equals("")) {
                    if (et.getChildren().size() == 0)
                         continue;
                    if (obj.containsKey(et.getName())) {
                         list = (List) obj.get(et.getName());
                    }
                    list.add(iterateElement(et));
                    obj.put(et.getName(), list);
               } else {
                    if (obj.containsKey(et.getName())) {
                         list = (List) obj.get(et.getName());
                    }
                    list.add(et.getTextTrim());
                    obj.put(et.getName(), list);
               }
          }
          return obj;
     } 


	/**
	 * 一个迭代方法
	 * 
	 * @param element
	 *            : org.jdom.Element
	 * @return java.util.Map 实例
	 */
	@SuppressWarnings("unchecked")
	private static Map iterateElement(Element element) {
		List node = element.getChildren();
		Element et = null;
		Map obj = new HashMap();
		 for (int i = 0; i < node.size(); i++) {  
	            et = ( org.jdom.Element) node.get(i);  
	            obj.put(et.getName(), et.getTextTrim());  
	        }  
		return obj;
	}

}
