package com.warrior.luminate.handler;

import cn.hutool.extra.mail.MailAccount;
import cn.hutool.extra.mail.MailUtil;
import com.google.common.base.Throwables;
import com.sun.mail.util.MailSSLSocketFactory;
import com.warrior.luminate.domain.TaskInfo;
import com.warrior.luminate.dto.EmailContentModel;
import com.warrior.luminate.enums.ChannelTypeEnums;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;

/**
 * @author WARRIOR
 * @version 1.0
 */

@Component
@Slf4j
public class EmailHandler extends AbstractHandler {
    @Value("${email.host}")
    private String host;
    @Value("${email.user}")
    private String user;
    @Value("${email.from}")
    private String from;
    @Value("${email.pass}")
    private String pass;
    @Value("${email.port}")
    private Integer port;

    public EmailHandler() {
        channelCode = ChannelTypeEnums.EMAIL.getCode();
    }

    @Override
    public boolean handler(TaskInfo taskInfo) {
        EmailContentModel emailContentModel = (EmailContentModel) taskInfo.getContentModel();
        MailAccount account = getAccount();
        try {
            MailUtil.send(account, taskInfo.getReceiver(), emailContentModel.getTitle(),
                    emailContentModel.getContent(), true);
            log.info("EmailHandler#handler success!,params:{}",taskInfo.toString());
        } catch (Exception e) {
            log.error("EmailHandler#handler fail!{},params:{}", Throwables.getStackTraceAsString(e), taskInfo);
            return false;
        }
        return true;
    }


    /**
     * 获取账号信息
     *
     * @return MailAccount
     */
    private MailAccount getAccount() {
        MailAccount account = new MailAccount();
        try {
            account.setHost(host).setPort(port);
            account.setUser(user).setPass(pass).setAuth(true);
            account.setFrom(from);

            MailSSLSocketFactory sf = new MailSSLSocketFactory();
            sf.setTrustAllHosts(true);
            account.setStarttlsEnable(true).setSslEnable(true).setCustomProperty("mail.smtp.ssl.socketFactory", sf);

            account.setTimeout(25000).setConnectionTimeout(25000);
        } catch (Exception e) {
            log.error("EmailHandler#getAccount fail!{}", Throwables.getStackTraceAsString(e));
        }
        return account;
    }
}
