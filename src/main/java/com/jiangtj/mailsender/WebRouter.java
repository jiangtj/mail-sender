package com.jiangtj.mailsender;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;

/**
 * Created by MrTT (jiang.taojie@foxmail.com)
 * 2019/9/12.
 */
@Configuration
public class WebRouter {

    @Bean
    public RouterFunction<ServerResponse> mainRouter() {
        return RouterFunctions.route(GET("/"), request -> {
            return ServerResponse.ok().body(BodyInserters.fromObject("hi hi!"));
        });
    }

}
