package com.lydb.entity.db_img_url;

import java.math.BigDecimal;
import java.util.Date;
import java.lang.String;
import java.lang.Double;
import java.lang.Integer;
import java.math.BigDecimal;
import javax.xml.soap.Text;
import java.sql.Blob;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.SequenceGenerator;
import org.jeecgframework.poi.excel.annotation.Excel;

/**   
 * @Title: Entity
 * @Description: 图片表
 * @author onlineGenerator
 * @date 2015-11-25 10:28:44
 * @version V1.0   
 *
 */
@Entity
@Table(name = "db_img_url", schema = "")
@SuppressWarnings("serial")
public class DbImgUrlEntity implements java.io.Serializable {
	/**主键*/
	private java.lang.String id;
	/**图片地址*/
	@Excel(name="图片地址")
	private java.lang.String imgUrl;
	/**分享id*/
	@Excel(name="分享id")
	private java.lang.String dbShareGoodsid;
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  主键
	 */
	@Id
	@GeneratedValue(generator = "paymentableGenerator")
	@GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
	@Column(name ="ID",nullable=false,length=36)
	public java.lang.String getId(){
		return this.id;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  主键
	 */
	public void setId(java.lang.String id){
		this.id = id;
	}
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  图片地址
	 */
	@Column(name ="IMG_URL",nullable=false,length=100)
	public java.lang.String getImgUrl(){
		return this.imgUrl;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  图片地址
	 */
	public void setImgUrl(java.lang.String imgUrl){
		this.imgUrl = imgUrl;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  分享id
	 */
	@Column(name ="DB_SHARE_GOODSID",nullable=true,length=36)
	public java.lang.String getDbShareGoodsid(){
		return this.dbShareGoodsid;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  分享id
	 */
	public void setDbShareGoodsid(java.lang.String dbShareGoodsid){
		this.dbShareGoodsid = dbShareGoodsid;
	}
}
