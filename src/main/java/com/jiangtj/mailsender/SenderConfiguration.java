package com.jiangtj.mailsender;

import com.jiangtj.mailsender.render.AsciidocRender;
import com.jiangtj.mailsender.render.MarkdownRender;
import com.jiangtj.mailsender.render.Render;
import com.jiangtj.mailsender.render.RenderHandler;
import freemarker.template.TemplateExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Created by jiang (jiang.taojie@foxmail.com)
 * 2019/9/12 20:33 End.
 */
@Configuration
public class SenderConfiguration {

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

}
