package com.jiangtj.mailsender.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Created by MrTT (jiang.taojie@foxmail.com)
 * 2019/9/18.
 */
@ConfigurationProperties("sender")
public class SenderProperties {

    @Getter
    private final MailProperties mail;

    @Getter
    private final TemplateProperties template;

    @Setter
    @Getter
    private String test;

    public SenderProperties(MailProperties mail, TemplateProperties template) {
        this.mail = mail;
        this.template = template;
    }

}
