package com.jiangtj.mailsender;

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
    private MailProperties mail;

    @Getter
    @Setter
    private String templateDir = "classpath:templates";

    public SenderProperties(MailProperties mail) {
        this.mail = mail;
    }

}
