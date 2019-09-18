package com.jiangtj.mailsender;

import com.jiangtj.mailsender.hander.RenderHandler;
import com.jiangtj.mailsender.hander.TemplateHandler;
import com.jiangtj.mailsender.render.AsciidocRender;
import com.jiangtj.mailsender.render.MarkdownRender;
import com.jiangtj.mailsender.render.Render;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
