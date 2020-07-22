package com.jiangtj.mailsender.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Created by MrTT (jiang.taojie@foxmail.com)
 * 2020/7/22.
 */
@Data
@ConfigurationProperties("sender.template")
public class TemplateProperties {
    private String version = "2.3.30";
    private String dir = "classpath:templates";
    private String encode = "UTF-8";
    private boolean logTemplateExceptions = false;
    private boolean wrapUncheckedExceptions = true;
    private boolean fallbackOnNullLoopVariable = false;
}
