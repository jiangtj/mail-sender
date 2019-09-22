package com.jiangtj.mailsender.dto;

import com.jiangtj.mailsender.SenderException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

/**
 * Created by jiang (jiang.taojie@foxmail.com)
 * 2019/9/22 14:23 End.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
public class Result {

    private HttpStatus status;
    private String message;

    public static Result ok() {
        return Result.of(HttpStatus.OK, null);
    }

    public static Result ok(String message) {
        return Result.of(HttpStatus.OK, message);
    }

    public static Result badRequest(String message) {
        return Result.of(HttpStatus.BAD_REQUEST, message);
    }

    public static Result serverError(String message) {
        return Result.of(HttpStatus.INTERNAL_SERVER_ERROR, message);
    }

    public static Mono<ServerResponse> transformToResponse(Mono<Result> result) {
        return result
                .flatMap(data -> {
                    return ServerResponse.status(data.getStatus()).syncBody(data);
                })
                .onErrorResume(SenderException.class, exception -> {
                    Result exceptionResult = exception.getResult();
                    return ServerResponse.status(exceptionResult.getStatus()).syncBody(exceptionResult);
                });
    }

}
