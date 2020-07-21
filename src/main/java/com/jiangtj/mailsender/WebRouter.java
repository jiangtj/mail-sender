package com.jiangtj.mailsender;

import com.jiangtj.mailsender.dto.Result;
import com.jiangtj.mailsender.hander.SenderHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
                    return ServerResponse.ok().bodyValue("Hello world!");
                })
                .POST("/send", senderHandler::send)
                .build()
                .filter((serverRequest, handlerFunction) -> {
                    return handlerFunction.handle(serverRequest).onErrorResume(IllegalArgumentException.class, e -> {
                        return ServerResponse.badRequest().bodyValue(Result.badRequest(e.getMessage()));
                    });
                });
    }

}
