package com.lydb.entity.db_token;

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
 * @Description: 用户token值
 * @author onlineGenerator
 * @date 2015-11-25 10:25:25
 * @version V1.0   
 *
 */
@Entity
@Table(name = "db_token", schema = "")
@SuppressWarnings("serial")
public class DbTokenEntity implements java.io.Serializable {
	/**主键*/
	private java.lang.String id;
	/**用户主表id*/
	@Excel(name="用户主表id")
	private java.lang.String dbAppClientid;
	/**最后登录时间*/
	@Excel(name="最后登录时间")
	private java.util.Date time;
	/**token值*/
	@Excel(name="token值")
	private java.lang.String tokenValue;
	
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
	 *@return: java.util.Date  最后登录时间
	 */
	@Column(name ="TIME",nullable=true,length=32)
	public java.util.Date getTime(){
		return this.time;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  最后登录时间
	 */
	public void setTime(java.util.Date time){
		this.time = time;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  token值
	 */
	@Column(name ="TOKEN_VALUE",nullable=true,length=36)
	public java.lang.String getTokenValue(){
		return this.tokenValue;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  token值
	 */
	public void setTokenValue(java.lang.String tokenValue){
		this.tokenValue = tokenValue;
	}
}
