package com.lydb.entity.db_zero_goods;

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
 * @Description: 0元商品主表
 * @author onlineGenerator
 * @date 2015-11-25 10:31:52
 * @version V1.0   
 *
 */
@Entity
@Table(name = "db_zero_goods", schema = "")
@SuppressWarnings("serial")
public class DbZeroGoodsEntity implements java.io.Serializable {
	/**主键*/
	private java.lang.String id;
	/**商品名称*/
	@Excel(name="商品名称")
	private java.lang.String zgoodsName;
	/**商品价格*/
	@Excel(name="商品价格")
	private java.lang.Integer zgoodsRmb;
	/**商品数量*/
	@Excel(name="商品数量")
	private java.lang.Integer zgoodsNum;
	/**商品介绍内容*/
	@Excel(name="商品介绍内容")
	private java.lang.String zgoodsContent;
	/**商品图片*/
	@Excel(name="商品图片")
	private java.lang.String zgoodsHeadurl;
	/**创建时间*/
	@Excel(name="创建时间")
	private java.util.Date createTime;
	/**商品状态*/
	@Excel(name="商品状态")
	private java.lang.String status;
	/**期数*/
	@Excel(name="期数")
	private java.lang.Integer zserialNum;
	/**商家信息*/
	@Excel(name="商家信息")
	private java.lang.String dbWebBusinessid;
	/**晒单的个数*/
	private java.lang.Integer zshareNum;
	/**开奖时间间隔*/
	private java.lang.Integer time;
	
	@Column(name ="TIME",nullable=true,length=12)
	public java.lang.Integer getTime() {
		return time;
	}

	public void setTime(java.lang.Integer time) {
		this.time = time;
	}

	@Column(name ="ZSHARE_NUM",nullable=true,length=12)
	public java.lang.Integer getZshareNum() {
		return zshareNum;
	}

	public void setZshareNum(java.lang.Integer zshareNum) {
		this.zshareNum = zshareNum;
	}

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
	 *@return: java.lang.String  商品名称
	 */
	@Column(name ="ZGOODS_NAME",nullable=true,length=20)
	public java.lang.String getZgoodsName(){
		return this.zgoodsName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  商品名称
	 */
	public void setZgoodsName(java.lang.String zgoodsName){
		this.zgoodsName = zgoodsName;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  商品价格
	 */
	@Column(name ="ZGOODS_RMB",nullable=true,length=32)
	public java.lang.Integer getZgoodsRmb(){
		return this.zgoodsRmb;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  商品价格
	 */
	public void setZgoodsRmb(java.lang.Integer zgoodsRmb){
		this.zgoodsRmb = zgoodsRmb;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  商品数量
	 */
	@Column(name ="ZGOODS_NUM",nullable=true,length=32)
	public java.lang.Integer getZgoodsNum(){
		return this.zgoodsNum;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  商品数量
	 */
	public void setZgoodsNum(java.lang.Integer zgoodsNum){
		this.zgoodsNum = zgoodsNum;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  商品介绍内容
	 */
	@Column(name ="ZGOODS_CONTENT",nullable=true,length=150)
	public java.lang.String getZgoodsContent(){
		return this.zgoodsContent;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  商品介绍内容
	 */
	public void setZgoodsContent(java.lang.String zgoodsContent){
		this.zgoodsContent = zgoodsContent;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  商品图片
	 */
	@Column(name ="ZGOODS_HEADURL",nullable=true,length=100)
	public java.lang.String getZgoodsHeadurl(){
		return this.zgoodsHeadurl;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  商品图片
	 */
	public void setZgoodsHeadurl(java.lang.String zgoodsHeadurl){
		this.zgoodsHeadurl = zgoodsHeadurl;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  创建时间
	 */
	@Column(name ="CREATE_TIME",nullable=true,length=32)
	public java.util.Date getCreateTime(){
		return this.createTime;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  创建时间
	 */
	public void setCreateTime(java.util.Date createTime){
		this.createTime = createTime;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  商品状态
	 */
	@Column(name ="STATUS",nullable=true,length=10)
	public java.lang.String getStatus(){
		return this.status;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  商品状态
	 */
	public void setStatus(java.lang.String status){
		this.status = status;
	}
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  商家信息
	 */
	@Column(name ="DB_WEB_BUSINESSID",nullable=false,length=36)
	public java.lang.String getDbWebBusinessid(){
		return this.dbWebBusinessid;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  商家信息
	 */
	public void setDbWebBusinessid(java.lang.String dbWebBusinessid){
		this.dbWebBusinessid = dbWebBusinessid;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  期数
	 */
	@Column(name ="ZSERIAL_NUM",nullable=false,length=32)
	public java.lang.Integer getZserialNum(){
		return this.zserialNum;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  期数
	 */
	public void setZserialNum(java.lang.Integer zserialNum){
		this.zserialNum = zserialNum;
	}
}
