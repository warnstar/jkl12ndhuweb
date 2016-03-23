package com.lydb.entity.db_goods;

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
 * @Description: 商品主表
 * @author onlineGenerator
 * @date 2015-11-25 10:27:17
 * @version V1.0   
 *
 */
@Entity
@Table(name = "db_goods", schema = "")
@SuppressWarnings("serial")
public class DbGoodsEntity implements java.io.Serializable {
	/**主键*/
	private java.lang.String id;
	/**商家主表id*/
	@Excel(name="商家主表id")
	private java.lang.String dbWebBusinessid;
	/**商品名称*/
	@Excel(name="商品名称")
	private java.lang.String goodsName;
	/**商品价格*/
	@Excel(name="商品价格")
	private java.lang.Integer goodsRmb;
	/**商品期数*/
	@Excel(name="商品期数")
	private java.lang.Integer goodsNum;
	/**商品简介*/
	@Excel(name="商品简介")
	private java.lang.String goodsContent;
	/**商品图片*/
	@Excel(name="商品图片")
	private java.lang.String goodsHeadurl;
	/**商品类型*/
	@Excel(name="商品类型")
	private java.lang.String goodsType;
	/**是否是10元商品*/
	@Excel(name="是否是10元商品")
	private java.lang.Integer goods10;
	/**创建时间*/
	@Excel(name="创建时间")
	private java.util.Date createTime;
	/**状态*/
	@Excel(name="状态")
	private java.lang.String status;
	/**期数*/
	@Excel(name="期数")
	private java.lang.Integer serialNum;
	/**期数*/
	@Excel(name="晒单个数，需要status>=4")
	private java.lang.Integer shareNum;
	
	@Column(name ="SHARE_NUM",nullable=true,length=11)
	public java.lang.Integer getShareNum() {
		return shareNum;
	}

	public void setShareNum(java.lang.Integer shareNum) {
		this.shareNum = shareNum;
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
	 *@return: java.lang.String  商家主表id
	 */
	@Column(name ="DB_WEB_BUSINESSID",nullable=false,length=36)
	public java.lang.String getDbWebBusinessid(){
		return this.dbWebBusinessid;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  商家主表id
	 */
	public void setDbWebBusinessid(java.lang.String dbWebBusinessid){
		this.dbWebBusinessid = dbWebBusinessid;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  商品名称
	 */
	@Column(name ="GOODS_NAME",nullable=true,length=50)
	public java.lang.String getGoodsName(){
		return this.goodsName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  商品名称
	 */
	public void setGoodsName(java.lang.String goodsName){
		this.goodsName = goodsName;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  商品价格
	 */
	@Column(name ="GOODS_RMB",nullable=true,length=32)
	public java.lang.Integer getGoodsRmb(){
		return this.goodsRmb;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  商品价格
	 */
	public void setGoodsRmb(java.lang.Integer goodsRmb){
		this.goodsRmb = goodsRmb;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  商品期数
	 */
	@Column(name ="GOODS_NUM",nullable=true,length=32)
	public java.lang.Integer getGoodsNum(){
		return this.goodsNum;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  商品期数
	 */
	public void setGoodsNum(java.lang.Integer goodsNum){
		this.goodsNum = goodsNum;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  商品简介
	 */
	@Column(name ="GOODS_CONTENT",nullable=true,length=150)
	public java.lang.String getGoodsContent(){
		return this.goodsContent;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  商品简介
	 */
	public void setGoodsContent(java.lang.String goodsContent){
		this.goodsContent = goodsContent;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  商品图片
	 */
	@Column(name ="GOODS_HEADURL",nullable=true,length=100)
	public java.lang.String getGoodsHeadurl(){
		return this.goodsHeadurl;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  商品图片
	 */
	public void setGoodsHeadurl(java.lang.String goodsHeadurl){
		this.goodsHeadurl = goodsHeadurl;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  商品类型
	 */
	@Column(name ="GOODS_TYPE",nullable=true,length=20)
	public java.lang.String getGoodsType(){
		return this.goodsType;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  商品类型
	 */
	public void setGoodsType(java.lang.String goodsType){
		this.goodsType = goodsType;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  是否是10元商品
	 */
	@Column(name ="GOODS_10",nullable=true,length=10)
	public java.lang.Integer getGoods10(){
		return this.goods10;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  是否是10元商品
	 */
	public void setGoods10(java.lang.Integer goods10){
		this.goods10 = goods10;
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
	 *@return: java.lang.Integer  状态
	 */
	@Column(name ="STATUS",nullable=false,length=11)
	public java.lang.String getStatus(){
		return this.status;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  状态
	 */
	public void setStatus(java.lang.String status){
		this.status = status;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  期数
	 */
	@Column(name ="SERIAL_NUM",nullable=false,length=32)
	public java.lang.Integer getSerialNum(){
		return this.serialNum;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  期数
	 */
	public void setSerialNum(java.lang.Integer serialNum){
		this.serialNum = serialNum;
	}
}
