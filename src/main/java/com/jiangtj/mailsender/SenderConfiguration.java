package com.jiangtj.mailsender;

import com.jiangtj.mailsender.hander.RenderHandler;
import com.jiangtj.mailsender.hander.SenderHandler;
import com.jiangtj.mailsender.hander.TemplateHandler;
import com.jiangtj.mailsender.render.AsciidocRender;
import com.jiangtj.mailsender.render.MarkdownRender;
import com.jiangtj.mailsender.render.Render;
import com.jiangtj.mailsender.repository.RecordRepository;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;

import java.io.IOException;
import java.util.List;

/**
 * Created by jiang (jiang.taojie@foxmail.com)
 * 2019/9/12 20:33 End.
 */
@Configuration
public class SenderConfiguration {

    @Bean
    @ConfigurationProperties("sender")
    public SenderProperties senderProperties(MailProperties mailProperties){
        return new SenderProperties(mailProperties);
    }

    @Bean
    public MarkdownRender markdownRender(){
        return new MarkdownRender();
    }

    @Bean
    public AsciidocRender asciidocRender(){
        return new AsciidocRender();
    }

    @Bean
    public RenderHandler renderHandler(List<Render> renders){
        return new RenderHandler(renders);
    }

    @Bean
    public TemplateHandler templateHandler(SenderProperties properties) throws IOException {
        return new TemplateHandler(properties);
    }

    @Bean
    public SenderHandler senderHandler(SenderProperties properties,
                                       RenderHandler renderHandler,
                                       TemplateHandler templateHandler,
                                       JavaMailSender javaMailSender,
                                       RecordRepository recordRepository) {
        SenderHandler senderHandler = new SenderHandler(properties);
        senderHandler.setRenderHandler(renderHandler);
        senderHandler.setTemplateHandler(templateHandler);
        senderHandler.setMailSender(javaMailSender);
        senderHandler.setRecordRepository(recordRepository);
        return senderHandler;
    }

}
