package com.lydb.entity.db_zgoods_single;

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
import javax.persistence.Version;

import org.hibernate.annotations.GenericGenerator;
import javax.persistence.SequenceGenerator;
import org.jeecgframework.poi.excel.annotation.Excel;

/**   
 * @Title: Entity
 * @Description: 0元商品单表
 * @author onlineGenerator
 * @date 2015-11-25 10:29:23
 * @version V1.0   
 *
 */
@Entity
@Table(name = "db_zgoods_single", schema = "")
@SuppressWarnings("serial")
public class DbZgoodsSingleEntity implements java.io.Serializable {
	/**主键*/
	private java.lang.String id;
	/**总期数*/
	@Excel(name="总期数")
	private java.lang.Integer goodsAllNum;
	/**开奖状态*/
	@Excel(name="开奖状态")
	private java.lang.Integer status;
	/**当前期数*/
	@Excel(name="当前期数")
	private java.lang.Integer goodsCurrentNum;
	/**开始时间*/
	@Excel(name="开始时间")
	private java.util.Date starTime;
	/**是否分享*/
	@Excel(name="是否分享")
	private java.lang.Integer share;

	/**当前参与人数*/
	@Excel(name="当前参与人数")
	private java.lang.Integer currentJoinNum;
	/**幸运号*/
	@Excel(name="幸运号")
	private java.lang.String luckyId;
	/**用户主表id*/
	@Excel(name="用户主表id")
	private java.lang.String dbAppClientid;
	/**0元商品主表id*/
	@Excel(name="0元商品主表id")
	private java.lang.String dbZeroGoodsid;
	/**开奖时间*/
	@Excel(name="开奖时间")
	private java.util.Date openTime;

	private java.util.Date time;
	
/**设置乐观锁  解决并发中的问题*/
	
	private java.lang.Integer optlock;
	
	@Version     
	@Column(name="OPTLOCK")
	public java.lang.Integer getOptlock() {
		return this.optlock;
	}

	public void setOptlock(java.lang.Integer optlock) {
		this.optlock = optlock;
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
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  总期数
	 */
	@Column(name ="GOODS_ALL_NUM",nullable=true,length=32)
	public java.lang.Integer getGoodsAllNum(){
		return this.goodsAllNum;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  总期数
	 */
	public void setGoodsAllNum(java.lang.Integer goodsAllNum){
		this.goodsAllNum = goodsAllNum;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  开奖状态
	 */
	@Column(name ="STATUS",nullable=true,length=10)
	public java.lang.Integer getStatus(){
		return this.status;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  开奖状态
	 */
	public void setStatus(java.lang.Integer status){
		this.status = status;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  当前期数
	 */
	@Column(name ="GOODS_CURRENT_NUM",nullable=true,length=32)
	public java.lang.Integer getGoodsCurrentNum(){
		return this.goodsCurrentNum;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  当前期数
	 */
	public void setGoodsCurrentNum(java.lang.Integer goodsCurrentNum){
		this.goodsCurrentNum = goodsCurrentNum;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  开始时间
	 */
	@Column(name ="STAR_TIME",nullable=true,length=32)
	public java.util.Date getStarTime(){
		return this.starTime;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  开始时间
	 */
	public void setStarTime(java.util.Date starTime){
		this.starTime = starTime;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  是否分享
	 */
	@Column(name ="SHARE",nullable=true,length=10)
	public java.lang.Integer getShare(){
		return this.share;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  是否分享
	 */
	public void setShare(java.lang.Integer share){
		this.share = share;
	}



	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  当前参与人数
	 */
	@Column(name ="CURRENT_JOIN_NUM",nullable=true,length=32)
	public java.lang.Integer getCurrentJoinNum(){
		return this.currentJoinNum;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  当前参与人数
	 */
	public void setCurrentJoinNum(java.lang.Integer currentJoinNum){
		this.currentJoinNum = currentJoinNum;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  幸运号
	 */
	@Column(name ="LUCKY_ID",nullable=true,length=36)
	public java.lang.String getLuckyId(){
		return this.luckyId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  幸运号
	 */
	public void setLuckyId(java.lang.String luckyId){
		this.luckyId = luckyId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  用户主表id
	 */
	@Column(name ="DB_APP_CLIENTID",nullable=true,length=36)
	public java.lang.String getDbAppClientid(){
		return this.dbAppClientid;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  用户主表id
	 */
	public void setDbAppClientid(java.lang.String dbAppClientid){
		this.dbAppClientid = dbAppClientid;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  0元商品主表id
	 */
	@Column(name ="DB_ZERO_GOODSID",nullable=false,length=36)
	public java.lang.String getDbZeroGoodsid(){
		return this.dbZeroGoodsid;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  0元商品主表id
	 */
	public void setDbZeroGoodsid(java.lang.String dbZeroGoodsid){
		this.dbZeroGoodsid = dbZeroGoodsid;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  开奖时间
	 */
	@Column(name ="OPEN_TIME",nullable=true,length=32)
	public java.util.Date getOpenTime(){
		return this.openTime;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  开奖时间
	 */
	public void setTime(java.util.Date time){
		this.time = time;
	}
	@Column(name ="time",nullable=true,length=32)
	public java.util.Date getTime(){
		return this.time;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  开奖时间
	 */
	public void setOpenTime(java.util.Date openTime){
		this.openTime = openTime;
	}
}
