package com.lydb.entity.db_join_client;

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
 * @Description: 参与夺宝的用户记录
 * @author onlineGenerator
 * @date 2015-11-25 10:28:17
 * @version V1.0   
 *
 */
@Entity
@Table(name = "db_join_client", schema = "")
@SuppressWarnings("serial")
public class DbJoinClientEntity implements java.io.Serializable {
	/**主键*/
	private java.lang.Integer id;
	/**商品单表id*/
	@Excel(name="商品单表id")
	private java.lang.String dbGoodsSingle;
	/**用户主表id*/
	@Excel(name="用户主表id")
	private java.lang.String dbAppClientid;
	/**参与时间*/
	@Excel(name="参与时间")
	private java.lang.String joinTime;

	/**参与抽奖码*/
	@Excel(name="参与抽奖码")
	private java.lang.String dbCode;
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
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name ="ID",nullable=false,length=36)
	public java.lang.Integer getId(){
		return this.id;
	}

	public void setId(java.lang.Integer id){
		this.id = id;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  商品单表id
	 */
	@Column(name ="DB_GOODS_SINGLE",nullable=false,length=36)
	public java.lang.String getDbGoodsSingle(){
		return this.dbGoodsSingle;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  商品单表id
	 */
	public void setDbGoodsSingle(java.lang.String dbGoodsSingle){
		this.dbGoodsSingle = dbGoodsSingle;
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
	 *@return: java.lang.String  参与抽奖码
	 */
	@Column(name ="DB_CODE",nullable=true,length=36)
	public java.lang.String getDbCode(){
		return this.dbCode;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  参与抽奖码
	 */
	public void setDbCode(java.lang.String dbCode){
		this.dbCode = dbCode;
	}
}
