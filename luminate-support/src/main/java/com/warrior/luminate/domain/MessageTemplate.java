package com.warrior.luminate.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Builder;

import java.io.Serializable;

/**
 * 消息模板信息
 * @TableName message_template
 * @author warrior
 */
@Builder
@TableName(value ="message_template")
public class MessageTemplate implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 标题
     */
    private String name;

    /**
     * 当前消息审核状态： 10.待审核 20.审核成功 30.被拒绝
     */
    private Integer auditStatus;

    /**
     * 工单ID
     */
    private String flowId;

    /**
     * 当前消息状态：10.新建 20.停用 30.启用 40.等待发送 50.发送中 60.发送成功 70.发送失败
     */
    private Integer msgStatus;

    /**
     * 消息的发送ID类型：10. userId 20.did 30.手机号 40.openId 50.email
     */
    private Integer idType;

    /**
     * 消息发送渠道：10.IM 20.Push 30.短信 40.Email 50.公众号 60.小程序
     */
    private Integer sendChannel;

    /**
     * 10.运营类 20.技术类接口调用
     */
    private Integer templateType;

    /**
     * 10.通知类消息 20.营销类消息 30.验证码类消息
     */
    private Integer msgType;

    /**
     * 期望发送时间：立即发送.10 定时任务以及周期任务.cron表达式
     */
    private String expectPushTime;

    /**
     * 消息内容 占位符用{$var}表示
     */
    private String msgContent;

    /**
     * 发送账号 一个渠道下可存在多个账号
     */
    private Integer sendAccount;

    /**
     * 创建者
     */
    private String creator;

    /**
     * 更新者
     */
    private String updator;

    /**
     * 审核人
     */
    private String auditor;

    /**
     * 业务方团队
     */
    private String team;

    /**
     * 业务方
     */
    private String proposer;

    /**
     * 是否删除：0.不删除 1.删除
     */
    private Integer isDeleted;

    /**
     * 创建时间
     */
    private Integer created;

    /**
     * 更新时间
     */
    private Integer updated;

    /**
     * 去重时间 单位小时
     */
    private Integer deduplicationTime;

    /**
     * 是否夜间屏蔽：0.夜间不屏蔽 1.夜间屏蔽
     */
    private Integer isNightShield;

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
     * 标题
     */
    public String getName() {
        return name;
    }

    /**
     * 标题
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 当前消息审核状态： 10.待审核 20.审核成功 30.被拒绝
     */
    public Integer getAuditStatus() {
        return auditStatus;
    }

    /**
     * 当前消息审核状态： 10.待审核 20.审核成功 30.被拒绝
     */
    public void setAuditStatus(Integer auditStatus) {
        this.auditStatus = auditStatus;
    }

    /**
     * 工单ID
     */
    public String getFlowId() {
        return flowId;
    }

    /**
     * 工单ID
     */
    public void setFlowId(String flowId) {
        this.flowId = flowId;
    }

    /**
     * 当前消息状态：10.新建 20.停用 30.启用 40.等待发送 50.发送中 60.发送成功 70.发送失败
     */
    public Integer getMsgStatus() {
        return msgStatus;
    }

    /**
     * 当前消息状态：10.新建 20.停用 30.启用 40.等待发送 50.发送中 60.发送成功 70.发送失败
     */
    public void setMsgStatus(Integer msgStatus) {
        this.msgStatus = msgStatus;
    }

    /**
     * 消息的发送ID类型：10. userId 20.did 30.手机号 40.openId 50.email
     */
    public Integer getIdType() {
        return idType;
    }

    /**
     * 消息的发送ID类型：10. userId 20.did 30.手机号 40.openId 50.email
     */
    public void setIdType(Integer idType) {
        this.idType = idType;
    }

    /**
     * 消息发送渠道：10.IM 20.Push 30.短信 40.Email 50.公众号 60.小程序
     */
    public Integer getSendChannel() {
        return sendChannel;
    }

    /**
     * 消息发送渠道：10.IM 20.Push 30.短信 40.Email 50.公众号 60.小程序
     */
    public void setSendChannel(Integer sendChannel) {
        this.sendChannel = sendChannel;
    }

    /**
     * 10.运营类 20.技术类接口调用
     */
    public Integer getTemplateType() {
        return templateType;
    }

    /**
     * 10.运营类 20.技术类接口调用
     */
    public void setTemplateType(Integer templateType) {
        this.templateType = templateType;
    }

    /**
     * 10.通知类消息 20.营销类消息 30.验证码类消息
     */
    public Integer getMsgType() {
        return msgType;
    }

    /**
     * 10.通知类消息 20.营销类消息 30.验证码类消息
     */
    public void setMsgType(Integer msgType) {
        this.msgType = msgType;
    }

    /**
     * 期望发送时间：立即发送.10 定时任务以及周期任务.cron表达式
     */
    public String getExpectPushTime() {
        return expectPushTime;
    }

    /**
     * 期望发送时间：立即发送.10 定时任务以及周期任务.cron表达式
     */
    public void setExpectPushTime(String expectPushTime) {
        this.expectPushTime = expectPushTime;
    }

    /**
     * 消息内容 占位符用{$var}表示
     */
    public String getMsgContent() {
        return msgContent;
    }

    /**
     * 消息内容 占位符用{$var}表示
     */
    public void setMsgContent(String msgContent) {
        this.msgContent = msgContent;
    }

    /**
     * 发送账号 一个渠道下可存在多个账号
     */
    public Integer getSendAccount() {
        return sendAccount;
    }

    /**
     * 发送账号 一个渠道下可存在多个账号
     */
    public void setSendAccount(Integer sendAccount) {
        this.sendAccount = sendAccount;
    }

    /**
     * 创建者
     */
    public String getCreator() {
        return creator;
    }

    /**
     * 创建者
     */
    public void setCreator(String creator) {
        this.creator = creator;
    }

    /**
     * 更新者
     */
    public String getUpdator() {
        return updator;
    }

    /**
     * 更新者
     */
    public void setUpdator(String updator) {
        this.updator = updator;
    }

    /**
     * 审核人
     */
    public String getAuditor() {
        return auditor;
    }

    /**
     * 审核人
     */
    public void setAuditor(String auditor) {
        this.auditor = auditor;
    }

    /**
     * 业务方团队
     */
    public String getTeam() {
        return team;
    }

    /**
     * 业务方团队
     */
    public void setTeam(String team) {
        this.team = team;
    }

    /**
     * 业务方
     */
    public String getProposer() {
        return proposer;
    }

    /**
     * 业务方
     */
    public void setProposer(String proposer) {
        this.proposer = proposer;
    }

    /**
     * 是否删除：0.不删除 1.删除
     */
    public Integer getIsDeleted() {
        return isDeleted;
    }

    /**
     * 是否删除：0.不删除 1.删除
     */
    public void setIsDeleted(Integer isDeleted) {
        this.isDeleted = isDeleted;
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

    /**
     * 去重时间 单位小时
     */
    public Integer getDeduplicationTime() {
        return deduplicationTime;
    }

    /**
     * 去重时间 单位小时
     */
    public void setDeduplicationTime(Integer deduplicationTime) {
        this.deduplicationTime = deduplicationTime;
    }

    /**
     * 是否夜间屏蔽：0.夜间不屏蔽 1.夜间屏蔽
     */
    public Integer getIsNightShield() {
        return isNightShield;
    }

    /**
     * 是否夜间屏蔽：0.夜间不屏蔽 1.夜间屏蔽
     */
    public void setIsNightShield(Integer isNightShield) {
        this.isNightShield = isNightShield;
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
        MessageTemplate other = (MessageTemplate) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getName() == null ? other.getName() == null : this.getName().equals(other.getName()))
            && (this.getAuditStatus() == null ? other.getAuditStatus() == null : this.getAuditStatus().equals(other.getAuditStatus()))
            && (this.getFlowId() == null ? other.getFlowId() == null : this.getFlowId().equals(other.getFlowId()))
            && (this.getMsgStatus() == null ? other.getMsgStatus() == null : this.getMsgStatus().equals(other.getMsgStatus()))
            && (this.getIdType() == null ? other.getIdType() == null : this.getIdType().equals(other.getIdType()))
            && (this.getSendChannel() == null ? other.getSendChannel() == null : this.getSendChannel().equals(other.getSendChannel()))
            && (this.getTemplateType() == null ? other.getTemplateType() == null : this.getTemplateType().equals(other.getTemplateType()))
            && (this.getMsgType() == null ? other.getMsgType() == null : this.getMsgType().equals(other.getMsgType()))
            && (this.getExpectPushTime() == null ? other.getExpectPushTime() == null : this.getExpectPushTime().equals(other.getExpectPushTime()))
            && (this.getMsgContent() == null ? other.getMsgContent() == null : this.getMsgContent().equals(other.getMsgContent()))
            && (this.getSendAccount() == null ? other.getSendAccount() == null : this.getSendAccount().equals(other.getSendAccount()))
            && (this.getCreator() == null ? other.getCreator() == null : this.getCreator().equals(other.getCreator()))
            && (this.getUpdator() == null ? other.getUpdator() == null : this.getUpdator().equals(other.getUpdator()))
            && (this.getAuditor() == null ? other.getAuditor() == null : this.getAuditor().equals(other.getAuditor()))
            && (this.getTeam() == null ? other.getTeam() == null : this.getTeam().equals(other.getTeam()))
            && (this.getProposer() == null ? other.getProposer() == null : this.getProposer().equals(other.getProposer()))
            && (this.getIsDeleted() == null ? other.getIsDeleted() == null : this.getIsDeleted().equals(other.getIsDeleted()))
            && (this.getCreated() == null ? other.getCreated() == null : this.getCreated().equals(other.getCreated()))
            && (this.getUpdated() == null ? other.getUpdated() == null : this.getUpdated().equals(other.getUpdated()))
            && (this.getDeduplicationTime() == null ? other.getDeduplicationTime() == null : this.getDeduplicationTime().equals(other.getDeduplicationTime()))
            && (this.getIsNightShield() == null ? other.getIsNightShield() == null : this.getIsNightShield().equals(other.getIsNightShield()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getName() == null) ? 0 : getName().hashCode());
        result = prime * result + ((getAuditStatus() == null) ? 0 : getAuditStatus().hashCode());
        result = prime * result + ((getFlowId() == null) ? 0 : getFlowId().hashCode());
        result = prime * result + ((getMsgStatus() == null) ? 0 : getMsgStatus().hashCode());
        result = prime * result + ((getIdType() == null) ? 0 : getIdType().hashCode());
        result = prime * result + ((getSendChannel() == null) ? 0 : getSendChannel().hashCode());
        result = prime * result + ((getTemplateType() == null) ? 0 : getTemplateType().hashCode());
        result = prime * result + ((getMsgType() == null) ? 0 : getMsgType().hashCode());
        result = prime * result + ((getExpectPushTime() == null) ? 0 : getExpectPushTime().hashCode());
        result = prime * result + ((getMsgContent() == null) ? 0 : getMsgContent().hashCode());
        result = prime * result + ((getSendAccount() == null) ? 0 : getSendAccount().hashCode());
        result = prime * result + ((getCreator() == null) ? 0 : getCreator().hashCode());
        result = prime * result + ((getUpdator() == null) ? 0 : getUpdator().hashCode());
        result = prime * result + ((getAuditor() == null) ? 0 : getAuditor().hashCode());
        result = prime * result + ((getTeam() == null) ? 0 : getTeam().hashCode());
        result = prime * result + ((getProposer() == null) ? 0 : getProposer().hashCode());
        result = prime * result + ((getIsDeleted() == null) ? 0 : getIsDeleted().hashCode());
        result = prime * result + ((getCreated() == null) ? 0 : getCreated().hashCode());
        result = prime * result + ((getUpdated() == null) ? 0 : getUpdated().hashCode());
        result = prime * result + ((getDeduplicationTime() == null) ? 0 : getDeduplicationTime().hashCode());
        result = prime * result + ((getIsNightShield() == null) ? 0 : getIsNightShield().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", name=").append(name);
        sb.append(", auditStatus=").append(auditStatus);
        sb.append(", flowId=").append(flowId);
        sb.append(", msgStatus=").append(msgStatus);
        sb.append(", idType=").append(idType);
        sb.append(", sendChannel=").append(sendChannel);
        sb.append(", templateType=").append(templateType);
        sb.append(", msgType=").append(msgType);
        sb.append(", expectPushTime=").append(expectPushTime);
        sb.append(", msgContent=").append(msgContent);
        sb.append(", sendAccount=").append(sendAccount);
        sb.append(", creator=").append(creator);
        sb.append(", updator=").append(updator);
        sb.append(", auditor=").append(auditor);
        sb.append(", team=").append(team);
        sb.append(", proposer=").append(proposer);
        sb.append(", isDeleted=").append(isDeleted);
        sb.append(", created=").append(created);
        sb.append(", updated=").append(updated);
        sb.append(", deduplicationTime=").append(deduplicationTime);
        sb.append(", isNightShield=").append(isNightShield);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}