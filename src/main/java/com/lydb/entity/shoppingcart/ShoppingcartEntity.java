package com.lydb.entity.shoppingcart;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.*;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

/**   
 * @Title: Entity
 * @Description: 购物车
 * @author zhangdaihao
 * @date 2016-01-03 16:21:21
 * @version V1.0   
 *
 */
@Entity
@Table(name = "shoppingcart", schema = "")
@DynamicUpdate(true)
@DynamicInsert(true)
@SuppressWarnings("serial")
public class ShoppingcartEntity implements java.io.Serializable {
	/**id*/
	private java.lang.String id;
	/**商品id*/
	private java.lang.String dbgoodsid;
	/**用户的id*/
	private java.lang.String dbappclientid;
	/**数量*/
	private java.lang.Integer number;
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
	 *@return: java.lang.String  id
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
	 *@param: java.lang.String  id
	 */
	public void setId(java.lang.String id){
		this.id = id;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  商品id
	 */
	@Column(name ="DBGOODSID",nullable=false,length=36)
	public java.lang.String getDbgoodsid(){
		return this.dbgoodsid;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  商品id
	 */
	public void setDbgoodsid(java.lang.String dbgoodsid){
		this.dbgoodsid = dbgoodsid;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  用户的id
	 */
	@Column(name ="DBAPPCLIENTID",nullable=false,length=36)
	public java.lang.String getDbappclientid(){
		return this.dbappclientid;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  用户的id
	 */
	public void setDbappclientid(java.lang.String dbappclientid){
		this.dbappclientid = dbappclientid;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  数量
	 */
	@Column(name ="NUMBER",nullable=false,precision=10,scale=0)
	public java.lang.Integer getNumber(){
		return this.number;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  数量
	 */
	public void setNumber(java.lang.Integer number){
		this.number = number;
	}
}
