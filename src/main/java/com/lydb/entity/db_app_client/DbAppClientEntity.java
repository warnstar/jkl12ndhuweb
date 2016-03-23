package com.lydb.entity.db_app_client;

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
 * @author onlineGenerator
 * @version V1.0
 * @Title: Entity
 * @Description: 用户主表
 * @date 2015-11-25 10:17:24
 */
@Entity
@Table(name = "db_app_client", schema = "")
@SuppressWarnings("serial")
public class DbAppClientEntity implements java.io.Serializable {
    /**
     * 主键
     */
    private java.lang.String id;
    /**
     * 手机号
     */
    @Excel(name = "手机号")
    private java.lang.String mobile;
    /**
     * 密码
     */
    @Excel(name = "密码")
    private java.lang.String password;
    /**
     * qq号
     */
    @Excel(name = "qq号")
    private java.lang.String clientQq;
    /**
     * 推广id
     */
    @Excel(name = "推广id")
    private java.lang.String popularizeId;
    /**
     * 头像图片
     */
    @Excel(name = "头像图片")
    private java.lang.String headImg;
    /**
     * 创建时间
     */
    @Excel(name = "创建时间")
    private java.util.Date createTime;


    /**
     * 用户昵称
     */
    @Excel(name = "用户昵称")
    private java.lang.String clientName;
    /**
     * 用户邀请人数，与邀请获得抢币一致
     */
    @Excel(name = "用户邀请人数，与邀请获得抢币一致")
    private java.lang.Integer inviteNum;
    /**
     * 用户的参与次数
     */
    @Excel(name = "用户的参与次数")
    private java.lang.Integer joinNum;
    /**
     * 用户的中奖次数
     */
    @Excel(name = "用户的中奖次数")
    private java.lang.Integer winNum;
    /**
     * 用户的登录时间
     *
     * @return
     */
    @Excel(name = "登录时间")
    private java.util.Date loginTime;

    /**
     * 用户参加的ip地址
     */
    @Excel(name = "用户参加的ip地址")
    private java.lang.String ipAddress;
    /**
     * ip地址对应的地理信息
     */
    @Excel(name = "ip地址对应的地理信息")
    private java.lang.String addressInfo;
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


    @Column(name = "LOGIN_TIME", nullable = true, length = 32)
    public java.util.Date getLoginTime() {
        return loginTime;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String  用户参加的ip地址
     */
    @Column(name = "IP_ADDRESS", nullable = true, length = 32)
    public java.lang.String getIpAddress() {
        return this.ipAddress;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String  用户参加的ip地址
     */
    public void setIpAddress(java.lang.String ipAddress) {
        this.ipAddress = ipAddress;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String  ip地址对应的地理信息
     */
    @Column(name = "ADDRESS_INFO", nullable = true, length = 32)
    public java.lang.String getAddressInfo() {
        return this.addressInfo;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String  ip地址对应的地理信息
     */
    public void setAddressInfo(java.lang.String addressInfo) {
        this.addressInfo = addressInfo;
    }

    public void setLoginTime(java.util.Date loginTime) {
        this.loginTime = loginTime;
    }

    @Column(name = "CLIENT_NAME", nullable = true, length = 50)
    public java.lang.String getClientName() {
        return clientName;
    }

    public void setClientName(java.lang.String clientName) {
        this.clientName = clientName;
    }

    @Column(name = "INVITE_NUM", nullable = true, length = 12)
    public java.lang.Integer getInviteNum() {
        return inviteNum;
    }

    public void setInviteNum(java.lang.Integer inviteNum) {
        this.inviteNum = inviteNum;
    }

    @Column(name = "JOIN_NUM", nullable = true, length = 16)
    public java.lang.Integer getJoinNum() {
        return joinNum;
    }

    public void setJoinNum(java.lang.Integer joinNum) {
        this.joinNum = joinNum;
    }

    @Column(name = "WIN_NUM", nullable = true, length = 11)
    public java.lang.Integer getWinNum() {
        return winNum;
    }

    public void setWinNum(java.lang.Integer winNum) {
        this.winNum = winNum;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String  主键
     */
    @Id
    @GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
    @Column(name = "ID", nullable = false, length = 36)
    public java.lang.String getId() {
        return this.id;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String  主键
     */
    public void setId(java.lang.String id) {
        this.id = id;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String  手机号
     */
    @Column(name = "MOBILE", nullable = true, length = 11)
    public java.lang.String getMobile() {
        return this.mobile;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String  手机号
     */
    public void setMobile(java.lang.String mobile) {
        this.mobile = mobile;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String  密码
     */
    @Column(name = "PASSWORD", nullable = true, length = 45)
    public java.lang.String getPassword() {
        return this.password;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String  密码
     */
    public void setPassword(java.lang.String password) {
        this.password = password;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String  qq号
     */
    @Column(name = "CLIENT_QQ", nullable = true, length = 15)
    public java.lang.String getClientQq() {
        return this.clientQq;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String  qq号
     */
    public void setClientQq(java.lang.String clientQq) {
        this.clientQq = clientQq;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String  推广id
     */
    @Column(name = "POPULARIZE_ID", nullable = true, length = 32)
    public java.lang.String getPopularizeId() {
        return this.popularizeId;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String  推广id
     */
    public void setPopularizeId(java.lang.String popularizeId) {
        this.popularizeId = popularizeId;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String  头像图片
     */
    @Column(name = "HEAD_IMG", nullable = true, length = 50)
    public java.lang.String getHeadImg() {
        return this.headImg;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String  头像图片
     */
    public void setHeadImg(java.lang.String headImg) {
        this.headImg = headImg;
    }

    /**
     * 方法: 取得java.util.Date
     *
     * @return: java.util.Date  创建时间
     */
    @Column(name = "CREATE_TIME", nullable = true, length = 32)
    public java.util.Date getCreateTime() {
        return this.createTime;
    }

    /**
     * 方法: 设置java.util.Date
     *
     * @param: java.util.Date  创建时间
     */
    public void setCreateTime(java.util.Date createTime) {
        this.createTime = createTime;
    }
}
