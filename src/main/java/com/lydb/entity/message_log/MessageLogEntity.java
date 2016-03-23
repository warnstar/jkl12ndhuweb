package com.lydb.entity.message_log;

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
 * @Description: 推送消息历史记录
 * @author onlineGenerator
 * @date 2015-12-01 21:01:18
 * @version V1.0   
 *
 */
@Entity
@Table(name = "message_log", schema = "")
@SuppressWarnings("serial")
public class MessageLogEntity implements java.io.Serializable {
	/**主键*/
	private java.lang.Integer id;
	/**消息标题*/
	@Excel(name="消息标题")
	private java.lang.String messageTitle;
	/**消息简介*/
	@Excel(name="消息简介")
	private java.lang.String messageBrief;
	/**消息内容*/
	@Excel(name="消息内容")
	private java.lang.String messageContent;
	/**发送时间*/
	@Excel(name="发送时间")
	private java.util.Date createTime;
	/**发送者名称*/
	@Excel(name="发送者名称")
	private java.lang.String createName;
	/**0不是 1是  推送所有人*/
	@Excel(name="0不是 1是  推送所有人")
	private java.lang.Integer pullAll;
	/**推送个人的id*/
	@Excel(name="推送个人的id")
	private java.lang.String appClientid;
	/**类型0系统1中奖2发货3客服*/
	@Excel(name="类型0系统1中奖2发货3客服")
	private java.lang.Integer type;

	@Column(name ="TYPE",nullable=true,length=10)
	public java.lang.Integer getType() {
		return this.type;
	}

	public void setType(java.lang.Integer type) {
		this.type = type;
	}

	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  主键
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name ="ID",nullable=false,length=20)
	public java.lang.Integer getId(){
		return this.id;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  主键
	 */
	public void setId(java.lang.Integer id){
		this.id = id;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  消息标题
	 */
	@Column(name ="MESSAGE_TITLE",nullable=true,length=32)
	public java.lang.String getMessageTitle(){
		return this.messageTitle;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  消息标题
	 */
	public void setMessageTitle(java.lang.String messageTitle){
		this.messageTitle = messageTitle;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  消息简介
	 */
	@Column(name ="MESSAGE_BRIEF",nullable=true,length=50)
	public java.lang.String getMessageBrief(){
		return this.messageBrief;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  消息简介
	 */
	public void setMessageBrief(java.lang.String messageBrief){
		this.messageBrief = messageBrief;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  消息内容
	 */
	@Column(name ="MESSAGE_CONTENT",nullable=true,length=400)
	public java.lang.String getMessageContent(){
		return this.messageContent;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  消息内容
	 */
	public void setMessageContent(java.lang.String messageContent){
		this.messageContent = messageContent;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  发送时间
	 */
	@Column(name ="CREATE_TIME",nullable=true,length=32)
	public java.util.Date getCreateTime(){
		return this.createTime;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  发送时间
	 */
	public void setCreateTime(java.util.Date createTime){
		this.createTime = createTime;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  发送者名称
	 */
	@Column(name ="CREATE_NAME",nullable=true,length=32)
	public java.lang.String getCreateName(){
		return this.createName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  发送者名称
	 */
	public void setCreateName(java.lang.String createName){
		this.createName = createName;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  0不是 1是  推送所有人
	 */
	@Column(name ="PULL_ALL",nullable=true,length=4)
	public java.lang.Integer getPullAll(){
		return this.pullAll;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  0不是 1是  推送所有人
	 */
	public void setPullAll(java.lang.Integer pullAll){
		this.pullAll = pullAll;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  推送个人的id
	 */
	@Column(name ="APP_CLIENTID",nullable=true,length=36)
	public java.lang.String getAppClientid(){
		return this.appClientid;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  推送个人的id
	 */
	public void setAppClientid(java.lang.String appClientid){
		this.appClientid = appClientid;
	}
}
