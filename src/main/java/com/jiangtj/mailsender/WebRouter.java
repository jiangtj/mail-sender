package com.jiangtj.mailsender;

import com.jiangtj.mailsender.dto.SendDto;
import com.jiangtj.mailsender.render.RenderHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;

/**
 * Created by MrTT (jiang.taojie@foxmail.com)
 * 2019/9/12.
 */
@Configuration
public class WebRouter {

    @Autowired
    private RenderHandler renderHandler;

    @Bean
    public RouterFunction<ServerResponse> mainRouter() {
        return RouterFunctions.route(GET("/"), request -> {
            return ServerResponse.ok().body(BodyInserters.fromObject("hi hi!"));
        });
    }

    /**
     * Test only
     * todo remove
     */
    @Bean
    public RouterFunction<ServerResponse> testRouter() {
        return RouterFunctions.route(POST("/render"), request -> {
            Mono<String> result = request.bodyToMono(SendDto.class)
                    .map(dto -> renderHandler.render(dto.getRender(), dto.getContent()));
            return ServerResponse.ok().body(result, String.class);
        });
    }

}
