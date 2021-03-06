package com.lydb.entity.db_goods_single;

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
 * @Description: 单个商品表
 * @author onlineGenerator
 * @date 2015-11-25 10:28:00
 * @version V1.0   
 *
 */
@Entity
@Table(name = "db_goods_single", schema = "")
@SuppressWarnings("serial")
public class DbGoodsSingleEntity implements java.io.Serializable {
	/**主键*/
	private java.lang.String id;
	/**商品主表id*/
	@Excel(name="商品主表id")
	private java.lang.String dbGoodsid;
	/**商品当前期数*/
	@Excel(name="商品当前期数")
	private java.lang.Integer goodsCurrentNum;
	/**商品总期数*/
	@Excel(name="商品总期数")
	private java.lang.Integer goodsAllNum;
	/**开始夺宝时间*/
	@Excel(name="开始夺宝时间")
	private java.util.Date starTime;
	/**商品状态*/
	@Excel(name="商品状态")
	private java.lang.Integer status;
	/**是否分享*/
	@Excel(name="是否分享")
	private java.lang.Integer share;
	/**当前参与个数*/
	@Excel(name="当前参与个数")
	private java.lang.Integer currentJoinNum;
	/**商品参与总数*/
	@Excel(name="商品参与总数")
	private java.lang.Integer allJoinNum;
	/**幸运号*/
	@Excel(name="幸运号")
	private java.lang.String luckyId;
	/**中奖用户id*/
	@Excel(name="中奖用户id")
	private java.lang.String dbAppClientid;
	/**开奖时间*/
	@Excel(name="开奖时间")
	private java.util.Date openTime;
	/**设置乐观锁  解决并发中的问题*/
	
	private java.lang.Integer optlock;

	private java.util.Date time;
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

	@Version     
	@Column(name="OPTLOCK")
	public java.lang.Integer getOptlock() {
		return this.optlock;
	}

	public void setOptlock(java.lang.Integer optlock) {
		this.optlock = optlock;
	}

	/**
	 * 中奖者的参与次数
	 */
	private java.lang.Integer  clientJoinNum;
	
	
	
	@Column(name ="CLIENT_JOIN_NUM",nullable=true,length=32)
	public java.lang.Integer getClientJoinNum() {
		return clientJoinNum;
	}

	public void setClientJoinNum(java.lang.Integer clientJoinNum) {
		this.clientJoinNum = clientJoinNum;
	}

	/**
	 * 存中奖时的A值，避免重复计算
	 */
	private java.lang.String  avalue;
	
	
	@Column(name ="AVALUE",nullable=true,length=32)
	public java.lang.String getAvalue() {
		return avalue;
	}

	public void setAvalue(java.lang.String avalue) {
		this.avalue = avalue;
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
	 *@return: java.lang.String  商品主表id
	 */
	@Column(name ="DB_GOODSID",nullable=false,length=36)
	public java.lang.String getDbGoodsid(){
		return this.dbGoodsid;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  商品主表id
	 */
	public void setDbGoodsid(java.lang.String dbGoodsid){
		this.dbGoodsid = dbGoodsid;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  商品当前期数
	 */
	@Column(name ="GOODS_CURRENT_NUM",nullable=true,length=32)
	public java.lang.Integer getGoodsCurrentNum(){
		return this.goodsCurrentNum;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  商品当前期数
	 */
	public void setGoodsCurrentNum(java.lang.Integer goodsCurrentNum){
		this.goodsCurrentNum = goodsCurrentNum;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  商品总期数
	 */
	@Column(name ="GOODS_ALL_NUM",nullable=true,length=32)
	public java.lang.Integer getGoodsAllNum(){
		return this.goodsAllNum;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  商品总期数
	 */
	public void setGoodsAllNum(java.lang.Integer goodsAllNum){
		this.goodsAllNum = goodsAllNum;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  开始夺宝时间
	 */
	@Column(name ="STAR_TIME",nullable=true,length=32)
	public java.util.Date getStarTime(){
		return this.starTime;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  开始夺宝时间
	 */
	public void setStarTime(java.util.Date starTime){
		this.starTime = starTime;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  商品状态
	 */
	@Column(name ="STATUS",nullable=true,length=10)
	public java.lang.Integer getStatus(){
		return this.status;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  商品状态
	 */
	public void setStatus(java.lang.Integer status){
		this.status = status;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  是否分享
	 */
	@Column(name ="SHARE",nullable=true,length=32)
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
	 *@return: java.lang.Integer  当前参与个数
	 */
	@Column(name ="CURRENT_JOIN_NUM",nullable=true,length=32)
	public java.lang.Integer getCurrentJoinNum(){
		return this.currentJoinNum;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  当前参与个数
	 */
	public void setCurrentJoinNum(java.lang.Integer currentJoinNum){
		this.currentJoinNum = currentJoinNum;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  商品参与总数
	 */
	@Column(name ="ALL_JOIN_NUM",nullable=true,length=32)
	public java.lang.Integer getAllJoinNum(){
		return this.allJoinNum;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  商品参与总数
	 */
	public void setAllJoinNum(java.lang.Integer allJoinNum){
		this.allJoinNum = allJoinNum;
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
	 *@return: java.lang.String  中奖用户id
	 */
	@Column(name ="DB_APP_CLIENTID",nullable=true,length=36)
	public java.lang.String getDbAppClientid(){
		return this.dbAppClientid;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  中奖用户id
	 */
	public void setDbAppClientid(java.lang.String dbAppClientid){
		this.dbAppClientid = dbAppClientid;
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
	public void setOpenTime(java.util.Date openTime){
		this.openTime = openTime;
	}
}
