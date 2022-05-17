package com.jiangtj.mailsender;

import com.jiangtj.mailsender.properties.SenderProperties;
import com.jiangtj.mailsender.properties.TemplateProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Created by jiang (jiang.taojie@foxmail.com)
 * 2019/9/12 20:33 End.
 */
@Configuration
@EnableConfigurationProperties({ SenderProperties.class, TemplateProperties.class })
public class SenderConfiguration {

}
