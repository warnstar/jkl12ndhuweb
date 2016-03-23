package com.lydb.entity.db_goods_payment;

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
 * @Description: 商品缴费表
 * @author onlineGenerator
 * @date 2015-11-25 10:28:59
 * @version V1.0   
 *
 */
@Entity
@Table(name = "db_goods_payment", schema = "")
@SuppressWarnings("serial")
public class DbGoodsPaymentEntity implements java.io.Serializable {
	/**主键*/
	private java.lang.String id;
	/**商品主表id*/
	@Excel(name="商品主表id")
	private java.lang.String dbGoodsid;
	/**保证金*/
	@Excel(name="保证金")
	private java.lang.Double deposit;
	/**缴费时间*/
	@Excel(name="缴费时间")
	private java.util.Date starttime;
	/**服务费*/
	@Excel(name="服务费")
	private java.lang.Double servicefee;
	/**缴费状态*/
	@Excel(name="缴费状态")
	private java.lang.Integer paymentStatus;
	/**退款时间*/
	@Excel(name="退款时间")
	private java.util.Date endtime;
	/**订单号*/
	@Excel(name="订单号")
	private java.lang.String orderNum;
	
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
	@Column(name ="DB_GOODSID",nullable=false,length=32)
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
	 *@return: java.lang.Integer  保证金
	 */
	@Column(name ="DEPOSIT",nullable=true,length=32)
	public java.lang.Double getDeposit(){
		return this.deposit;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  保证金
	 */
	public void setDeposit(java.lang.Double deposit){
		this.deposit = deposit;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  缴费时间
	 */
	@Column(name ="STARTTIME",nullable=true,length=32)
	public java.util.Date getStarttime(){
		return this.starttime;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  缴费时间
	 */
	public void setStarttime(java.util.Date starttime){
		this.starttime = starttime;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  服务费
	 */
	@Column(name ="SERVICEFEE",nullable=true,length=32)
	public java.lang.Double getServicefee(){
		return this.servicefee;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  服务费
	 */
	public void setServicefee(java.lang.Double servicefee){
		this.servicefee = servicefee;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  缴费状态
	 */
	@Column(name ="PAYMENT_STATUS",nullable=false,length=10)
	public java.lang.Integer getPaymentStatus(){
		return this.paymentStatus;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  缴费状态
	 */
	public void setPaymentStatus(java.lang.Integer paymentStatus){
		this.paymentStatus = paymentStatus;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  退款时间
	 */
	@Column(name ="ENDTIME",nullable=true,length=32)
	public java.util.Date getEndtime(){
		return this.endtime;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  退款时间
	 */
	public void setEndtime(java.util.Date endtime){
		this.endtime = endtime;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  订单号
	 */
	@Column(name ="ORDER_NUM",nullable=true,length=36)
	public java.lang.String getOrderNum(){
		return this.orderNum;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  订单号
	 */
	public void setOrderNum(java.lang.String orderNum){
		this.orderNum = orderNum;
	}
}
