package com.jiangtj.mailsender;

import com.jiangtj.mailsender.hander.SenderHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

/**
 * Created by MrTT (jiang.taojie@foxmail.com)
 * 2019/9/12.
 */
@Configuration
public class WebRouter {

    @Autowired
    private SenderHandler senderHandler;

    @Bean
    public RouterFunction<ServerResponse> mainRouter() {
        return RouterFunctions.route()
                .GET("/", request -> {
                    return ServerResponse.ok().body(BodyInserters.fromObject("hi hi!"));
                })
                .POST("/send", senderHandler::send)
                .build();
    }

}
