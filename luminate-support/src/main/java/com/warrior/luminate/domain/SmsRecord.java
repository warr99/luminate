package com.warrior.luminate.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

/**
 * 短信记录信息
 * @TableName sms_record
 * @author warrior
 */
@TableName(value ="sms_record")
public class SmsRecord implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 消息模板ID
     */
    private Long messageTemplateId;

    /**
     * 手机号
     */
    private Long phone;

    /**
     * 发送短信渠道商的ID
     */
    private Integer supplierId;

    /**
     * 发送短信渠道商的名称
     */
    private String supplierName;

    /**
     * 短信发送的内容
     */
    private String msgContent;

    /**
     * 下发批次的ID
     */
    private String seriesId;

    /**
     * 计费条数
     */
    private Integer chargingNum;

    /**
     * 回执内容
     */
    private String reportContent;

    /**
     * 短信状态： 10.发送 20.成功 30.失败
     */
    private Integer status;

    /**
     * 发送日期：20211112
     */
    private Integer sendDate;

    /**
     * 创建时间
     */
    private Integer created;

    /**
     * 更新时间
     */
    private Integer updated;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    /**
     * 
     */
    public Long getId() {
        return id;
    }

    /**
     * 
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 消息模板ID
     */
    public Long getMessageTemplateId() {
        return messageTemplateId;
    }

    /**
     * 消息模板ID
     */
    public void setMessageTemplateId(Long messageTemplateId) {
        this.messageTemplateId = messageTemplateId;
    }

    /**
     * 手机号
     */
    public Long getPhone() {
        return phone;
    }

    /**
     * 手机号
     */
    public void setPhone(Long phone) {
        this.phone = phone;
    }

    /**
     * 发送短信渠道商的ID
     */
    public Integer getSupplierId() {
        return supplierId;
    }

    /**
     * 发送短信渠道商的ID
     */
    public void setSupplierId(Integer supplierId) {
        this.supplierId = supplierId;
    }

    /**
     * 发送短信渠道商的名称
     */
    public String getSupplierName() {
        return supplierName;
    }

    /**
     * 发送短信渠道商的名称
     */
    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    /**
     * 短信发送的内容
     */
    public String getMsgContent() {
        return msgContent;
    }

    /**
     * 短信发送的内容
     */
    public void setMsgContent(String msgContent) {
        this.msgContent = msgContent;
    }

    /**
     * 下发批次的ID
     */
    public String getSeriesId() {
        return seriesId;
    }

    /**
     * 下发批次的ID
     */
    public void setSeriesId(String seriesId) {
        this.seriesId = seriesId;
    }

    /**
     * 计费条数
     */
    public Integer getChargingNum() {
        return chargingNum;
    }

    /**
     * 计费条数
     */
    public void setChargingNum(Integer chargingNum) {
        this.chargingNum = chargingNum;
    }

    /**
     * 回执内容
     */
    public String getReportContent() {
        return reportContent;
    }

    /**
     * 回执内容
     */
    public void setReportContent(String reportContent) {
        this.reportContent = reportContent;
    }

    /**
     * 短信状态： 10.发送 20.成功 30.失败
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 短信状态： 10.发送 20.成功 30.失败
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 发送日期：20211112
     */
    public Integer getSendDate() {
        return sendDate;
    }

    /**
     * 发送日期：20211112
     */
    public void setSendDate(Integer sendDate) {
        this.sendDate = sendDate;
    }

    /**
     * 创建时间
     */
    public Integer getCreated() {
        return created;
    }

    /**
     * 创建时间
     */
    public void setCreated(Integer created) {
        this.created = created;
    }

    /**
     * 更新时间
     */
    public Integer getUpdated() {
        return updated;
    }

    /**
     * 更新时间
     */
    public void setUpdated(Integer updated) {
        this.updated = updated;
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        SmsRecord other = (SmsRecord) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getMessageTemplateId() == null ? other.getMessageTemplateId() == null : this.getMessageTemplateId().equals(other.getMessageTemplateId()))
            && (this.getPhone() == null ? other.getPhone() == null : this.getPhone().equals(other.getPhone()))
            && (this.getSupplierId() == null ? other.getSupplierId() == null : this.getSupplierId().equals(other.getSupplierId()))
            && (this.getSupplierName() == null ? other.getSupplierName() == null : this.getSupplierName().equals(other.getSupplierName()))
            && (this.getMsgContent() == null ? other.getMsgContent() == null : this.getMsgContent().equals(other.getMsgContent()))
            && (this.getSeriesId() == null ? other.getSeriesId() == null : this.getSeriesId().equals(other.getSeriesId()))
            && (this.getChargingNum() == null ? other.getChargingNum() == null : this.getChargingNum().equals(other.getChargingNum()))
            && (this.getReportContent() == null ? other.getReportContent() == null : this.getReportContent().equals(other.getReportContent()))
            && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
            && (this.getSendDate() == null ? other.getSendDate() == null : this.getSendDate().equals(other.getSendDate()))
            && (this.getCreated() == null ? other.getCreated() == null : this.getCreated().equals(other.getCreated()))
            && (this.getUpdated() == null ? other.getUpdated() == null : this.getUpdated().equals(other.getUpdated()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getMessageTemplateId() == null) ? 0 : getMessageTemplateId().hashCode());
        result = prime * result + ((getPhone() == null) ? 0 : getPhone().hashCode());
        result = prime * result + ((getSupplierId() == null) ? 0 : getSupplierId().hashCode());
        result = prime * result + ((getSupplierName() == null) ? 0 : getSupplierName().hashCode());
        result = prime * result + ((getMsgContent() == null) ? 0 : getMsgContent().hashCode());
        result = prime * result + ((getSeriesId() == null) ? 0 : getSeriesId().hashCode());
        result = prime * result + ((getChargingNum() == null) ? 0 : getChargingNum().hashCode());
        result = prime * result + ((getReportContent() == null) ? 0 : getReportContent().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        result = prime * result + ((getSendDate() == null) ? 0 : getSendDate().hashCode());
        result = prime * result + ((getCreated() == null) ? 0 : getCreated().hashCode());
        result = prime * result + ((getUpdated() == null) ? 0 : getUpdated().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", messageTemplateId=").append(messageTemplateId);
        sb.append(", phone=").append(phone);
        sb.append(", supplierId=").append(supplierId);
        sb.append(", supplierName=").append(supplierName);
        sb.append(", msgContent=").append(msgContent);
        sb.append(", seriesId=").append(seriesId);
        sb.append(", chargingNum=").append(chargingNum);
        sb.append(", reportContent=").append(reportContent);
        sb.append(", status=").append(status);
        sb.append(", sendDate=").append(sendDate);
        sb.append(", created=").append(created);
        sb.append(", updated=").append(updated);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}