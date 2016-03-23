package com.lydb.entity.db_app_client_rmb;

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
 * @Description: 用户资金表
 * @author onlineGenerator
 * @date 2015-11-25 10:24:16
 * @version V1.0   
 *
 */
@Entity
@Table(name = "db_app_client_rmb", schema = "")
@SuppressWarnings("serial")
public class DbAppClientRmbEntity implements java.io.Serializable {
	/**主键*/
	private java.lang.String id;
	/**用户主表id*/
	@Excel(name="用户主表id")
	private java.lang.String idAppClientid;
	/**用户抢币金额*/
	@Excel(name="用户抢币金额")
	private java.lang.Integer rmb;
	/**
	 * 用户积分
	 */
	private java.lang.Integer integrate;
	/**积分兑换总金额*/
	@Excel(name="积分兑换总金额")
	private java.lang.Integer integrateAll;
	/**邀请的抢币金额*/
	@Excel(name="邀请的抢币金额")
	private java.lang.Integer rmbInvite;
	/**今日收入的抢币*/
	@Excel(name="今日收入的抢币")
	private java.lang.Integer rmbToday;
	/**今日积分收入的抢币*/
	@Excel(name="今日积分收入的抢币")
	private java.lang.Integer integrateToday;

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


    @Column(name ="INTEGRATE",nullable=false,length=32)
	public java.lang.Integer getIntegrate() {
		return integrate;
	}

	public void setIntegrate(java.lang.Integer integrate) {
		this.integrate = integrate;
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
	@Column(name ="ID_APP_CLIENTID",nullable=false,length=36)
	public java.lang.String getIdAppClientid(){
		return this.idAppClientid;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  用户主表id
	 */
	public void setIdAppClientid(java.lang.String idAppClientid){
		this.idAppClientid = idAppClientid;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  用户抢币金额
	 */
	@Column(name ="RMB",nullable=false,length=32)
	public java.lang.Integer getRmb(){
		return this.rmb;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  用户抢币金额
	 */
	public void setRmb(java.lang.Integer rmb){
		this.rmb = rmb;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  积分兑换总金额
	 */
	@Column(name ="INTEGRATE_ALL",nullable=false,length=32)
	public java.lang.Integer getIntegrateAll(){
		return this.integrateAll;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  积分兑换总金额
	 */
	public void setIntegrateAll(java.lang.Integer integrateAll){
		this.integrateAll = integrateAll;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  邀请的抢币金额
	 */
	@Column(name ="RMB_INVITE",nullable=false,length=32)
	public java.lang.Integer getRmbInvite(){
		return this.rmbInvite;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  邀请的抢币金额
	 */
	public void setRmbInvite(java.lang.Integer rmbInvite){
		this.rmbInvite = rmbInvite;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  今日收入的抢币
	 */
	@Column(name ="RMB_TODAY",nullable=false,length=16)
	public java.lang.Integer getRmbToday(){
		return this.rmbToday;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  今日收入的抢币
	 */
	public void setRmbToday(java.lang.Integer rmbToday){
		this.rmbToday = rmbToday;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  今日积分收入的抢币
	 */
	@Column(name ="INTEGRATE_TODAY",nullable=false,length=16)
	public java.lang.Integer getIntegrateToday(){
		return this.integrateToday;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  今日积分收入的抢币
	 */
	public void setIntegrateToday(java.lang.Integer integrateToday){
		this.integrateToday = integrateToday;
	}
}
