package com.lydb.entity.db_exchange_rmb;

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
 * @Description: 积分兑换表
 * @author onlineGenerator
 * @date 2015-11-25 10:19:38
 * @version V1.0   
 *
 */
@Entity
@Table(name = "db_exchange_rmb", schema = "")
@SuppressWarnings("serial")
public class DbExchangeRmbEntity implements java.io.Serializable {
	/**主键*/
	private java.lang.String id;
	/**用户主表id*/
	@Excel(name="用户主表id")
	private java.lang.String dbAppClientid;
	/**兑换时间*/
	@Excel(name="兑换时间")
	private java.sql.Date time;
	/**兑换时间*/
	@Excel(name="兑换时间")
	private java.lang.Integer rmbNum;
	/**兑换状态*/
	@Excel(name="兑换状态")
	private java.lang.String status;
	
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
	 *@return: java.util.Date  兑换时间
	 */
	@Column(name ="TIME",nullable=true,length=32)
	public java.sql.Date getTime(){
		return this.time;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  兑换时间
	 */
	public void setTime(java.sql.Date time){
		this.time = time;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  兑换时间
	 */
	@Column(name ="RMB_NUM",nullable=false,length=10)
	public java.lang.Integer getRmbNum(){
		return this.rmbNum;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  兑换时间
	 */
	public void setRmbNum(java.lang.Integer rmbNum){
		this.rmbNum = rmbNum;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  兑换状态
	 */
	@Column(name ="STATUS",nullable=true,length=12)
	public java.lang.String getStatus(){
		return this.status;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  兑换状态
	 */
	public void setStatus(java.lang.String status){
		this.status = status;
	}
}
