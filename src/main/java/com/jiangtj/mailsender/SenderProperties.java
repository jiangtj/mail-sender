package com.jiangtj.mailsender;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.autoconfigure.mail.MailProperties;

/**
 * Created by MrTT (jiang.taojie@foxmail.com)
 * 2019/9/18.
 */
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
