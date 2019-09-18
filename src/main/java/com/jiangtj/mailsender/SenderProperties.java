package com.jiangtj.mailsender;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Created by MrTT (jiang.taojie@foxmail.com)
 * 2019/9/18.
 */
@Data
@Component
@ConfigurationProperties("sender")
public class SenderProperties {

    private String templateDir = "classpath:templates";

}
