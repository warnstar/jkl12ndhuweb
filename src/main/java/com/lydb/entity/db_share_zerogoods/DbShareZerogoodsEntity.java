package com.lydb.entity.db_share_zerogoods;

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
 * @Description: 零元商品的晒单
 * @author onlineGenerator
 * @date 2015-12-23 15:52:48
 * @version V1.0   
 *
 */
@Entity
@Table(name = "db_share_zerogoods", schema = "")
@SuppressWarnings("serial")
public class DbShareZerogoodsEntity implements java.io.Serializable {
	/**主键*/
	private java.lang.String id;
	/**商品单表*/
	@Excel(name="商品单表")
	private java.lang.String dbZgoodsSingleid;
	/**用户主表*/
	@Excel(name="用户主表")
	private java.lang.String dbAppClientid;
	/**分享标题*/
	@Excel(name="分享标题")
	private java.lang.String shareTitle;
	/**晒单内容*/
	@Excel(name="晒单内容")
	private java.lang.String shareContent;
	/**晒单时间*/
	@Excel(name="晒单时间")
	private java.util.Date shareTime;
	
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
	 *@return: java.lang.String  商品单表
	 */
	@Column(name ="DB_ZGOODS_SINGLEID",nullable=true,length=36)
	public java.lang.String getDbZgoodsSingleid(){
		return this.dbZgoodsSingleid;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  商品单表
	 */
	public void setDbZgoodsSingleid(java.lang.String dbZgoodsSingleid){
		this.dbZgoodsSingleid = dbZgoodsSingleid;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  用户主表
	 */
	@Column(name ="DB_APP_CLIENTID",nullable=true,length=36)
	public java.lang.String getDbAppClientid(){
		return this.dbAppClientid;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  用户主表
	 */
	public void setDbAppClientid(java.lang.String dbAppClientid){
		this.dbAppClientid = dbAppClientid;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  分享标题
	 */
	@Column(name ="SHARE_TITLE",nullable=true,length=32)
	public java.lang.String getShareTitle(){
		return this.shareTitle;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  分享标题
	 */
	public void setShareTitle(java.lang.String shareTitle){
		this.shareTitle = shareTitle;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  晒单内容
	 */
	@Column(name ="SHARE_CONTENT",nullable=true,length=200)
	public java.lang.String getShareContent(){
		return this.shareContent;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  晒单内容
	 */
	public void setShareContent(java.lang.String shareContent){
		this.shareContent = shareContent;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  晒单时间
	 */
	@Column(name ="SHARE_TIME",nullable=true,length=32)
	public java.util.Date getShareTime(){
		return this.shareTime;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  晒单时间
	 */
	public void setShareTime(java.util.Date shareTime){
		this.shareTime = shareTime;
	}
}
