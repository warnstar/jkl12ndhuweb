package com.lydb.entity.rule_table;

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
 * @Description: 规则编辑表
 * @author onlineGenerator
 * @date 2015-12-01 21:04:16
 * @version V1.0   
 *
 */
@Entity
@Table(name = "rule_table", schema = "")
@SuppressWarnings("serial")
public class RuleTableEntity implements java.io.Serializable {
	/**主键*/
	private java.lang.Integer id;
	/**规则名*/
	@Excel(name="规则名")
	private java.lang.String ruleName;
	/**规则内容*/
	@Excel(name="规则内容")
	private java.lang.String ruleContent;
	/**修改时间*/
	@Excel(name="修改时间")
	private java.util.Date createTime;
	/**修改者名称*/
	@Excel(name="修改者名称")
	private java.lang.String changeName;

	private java.lang.String title;
	private java.lang.String brief;

	@Column(name ="BRIEF",nullable=true,length=70)
	public String getBrief() {
		return brief;
	}

	public void setBrief(String brief) {
		this.brief = brief;
	}
	@Column(name ="TITLE",nullable=true,length=50)
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
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
	 *@return: java.lang.String  规则名
	 */
	@Column(name ="RULE_NAME",nullable=true,length=32)
	public java.lang.String getRuleName(){
		return this.ruleName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  规则名
	 */
	public void setRuleName(java.lang.String ruleName){
		this.ruleName = ruleName;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  规则内容
	 */
	@Column(name ="RULE_CONTENT",nullable=true,length=500)
	public java.lang.String getRuleContent(){
		return this.ruleContent;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  规则内容
	 */
	public void setRuleContent(java.lang.String ruleContent){
		this.ruleContent = ruleContent;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  修改时间
	 */
	@Column(name ="CREATE_TIME",nullable=true,length=32)
	public java.util.Date getCreateTime(){
		return this.createTime;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  修改时间
	 */
	public void setCreateTime(java.util.Date createTime){
		this.createTime = createTime;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  修改者名称
	 */
	@Column(name ="CHANGE_NAME",nullable=true,length=32)
	public java.lang.String getChangeName(){
		return this.changeName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  修改者名称
	 */
	public void setChangeName(java.lang.String changeName){
		this.changeName = changeName;
	}
}
