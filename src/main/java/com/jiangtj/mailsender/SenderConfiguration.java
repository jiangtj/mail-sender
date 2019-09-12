package com.jiangtj.mailsender;

import com.jiangtj.mailsender.render.MarkdownRender;
import com.jiangtj.mailsender.render.Render;
import com.jiangtj.mailsender.render.RenderHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
    public RenderHandler renderHandler(List<Render> renders){
        return new RenderHandler(renders);
    }

}
