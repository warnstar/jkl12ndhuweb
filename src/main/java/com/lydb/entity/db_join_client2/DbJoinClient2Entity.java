package com.lydb.entity.db_join_client2;

import java.math.BigDecimal;
import java.util.Date;
import java.lang.String;
import java.lang.Double;
import java.lang.Integer;
import java.math.BigDecimal;
import javax.persistence.*;
import javax.xml.soap.Text;
import java.sql.Blob;

import org.hibernate.annotations.GenericGenerator;
import org.jeecgframework.poi.excel.annotation.Excel;

/**   
 * @Title: Entity
 * @Description: 0元夺宝参与人表
 * @author onlineGenerator
 * @date 2015-11-25 10:29:37
 * @version V1.0   
 *
 */
@Entity
@Table(name = "db_join_client2", schema = "")
@SuppressWarnings("serial")
public class DbJoinClient2Entity implements java.io.Serializable {
	/**主键*/
	private java.lang.String id;
	/**用户主表id*/
	@Excel(name="用户主表id")
	private java.lang.String dbAppClientid;

	/**参与时间*/
	@Excel(name="参与时间")
	private java.lang.String joinTime;
	/**夺宝号*/
	@Excel(name="夺宝号")
	private java.lang.String dbCode;
	/**夺宝商品id*/
	@Excel(name="夺宝商品id")
	private java.lang.String dbZgoodsSingleid;

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
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  用户主表id
	 */
	@Column(name ="DB_APP_CLIENTID",nullable=false,length=36)
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
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  参与时间
	 */
	@Column(name ="JOIN_TIME",nullable=true,length=32)
	public java.lang.String getJoinTime(){
		return this.joinTime;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  参与时间
	 */
	public void setJoinTime(java.lang.String joinTime){
		this.joinTime = joinTime;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  夺宝号
	 */
	@Column(name ="DB_CODE",nullable=true,length=36)
	public java.lang.String getDbCode(){
		return this.dbCode;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  夺宝号
	 */
	public void setDbCode(java.lang.String dbCode){
		this.dbCode = dbCode;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  夺宝商品id
	 */
	@Column(name ="DB_ZGOODS_SINGLEID",nullable=false,length=36)
	public java.lang.String getDbZgoodsSingleid(){
		return this.dbZgoodsSingleid;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  夺宝商品id
	 */
	public void setDbZgoodsSingleid(java.lang.String dbZgoodsSingleid){
		this.dbZgoodsSingleid = dbZgoodsSingleid;
	}
}
