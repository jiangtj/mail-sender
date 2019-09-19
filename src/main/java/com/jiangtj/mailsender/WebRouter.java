package com.jiangtj.mailsender;

import com.jiangtj.mailsender.dto.SendDto;
import com.jiangtj.mailsender.dto.TemplateDto;
import com.jiangtj.mailsender.hander.RenderHandler;
import com.jiangtj.mailsender.hander.TemplateHandler;
import com.jiangtj.mailsender.model.Record;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by MrTT (jiang.taojie@foxmail.com)
 * 2019/9/12.
 */
@Configuration
public class WebRouter {

    @Autowired
    private RenderHandler renderHandler;
    @Autowired
    private TemplateHandler templateHandler;

    @Bean
    public RouterFunction<ServerResponse> mainRouter() {
        return RouterFunctions.route()
                .GET("/", request -> {
                    return ServerResponse.ok().body(BodyInserters.fromObject("hi hi!"));
                })
                .POST("/send", request -> {
                    Mono<Record> result = request.bodyToMono(SendDto.class)
                            // Set default value
                            .doOnNext(body -> {
                                if (body.getTemplate() == null) {
                                    body.setTemplate(new TemplateDto());
                                }
                                TemplateDto template = body.getTemplate();
                                if (template.getParams() == null) {
                                    template.setParams(new HashMap<>());
                                }
                            })
                            // Render content
                            .doOnNext(body -> {
                                String render = body.getRender();
                                String content = body.getContent();
                                String renderHtml = renderHandler.render(render, content);
                                if (StringUtils.isEmpty(renderHtml)) {
                                    return;
                                }
                                Map<String, Object> params = body.getTemplate().getParams();
                                params.put("bodyRaw", content);
                                params.put("body", renderHtml);
                            })
                            // Render template
                            .map(body -> {
                                TemplateDto template = body.getTemplate();
                                String html = templateHandler.handle(template.getName(), template.getParams());
                                Record record = new Record();
                                record.setContent(html);
                                return record;
                            })
                            // Send mail
                            .doOnNext(record -> {
                                //todo 发送邮件
                            });
                    //.thenReturn("success!");
                    return ServerResponse.ok().body(result, Record.class);
                })
                .build();
    }

}
