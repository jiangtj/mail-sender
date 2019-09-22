package com.jiangtj.mailsender.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jiangtj.mailsender.SenderException;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

/**
 * Created by jiang (jiang.taojie@foxmail.com)
 * 2019/9/22 14:23 End.
 */
@Data
public class Result {

    private HttpStatus status;
    private String message;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime time = LocalDateTime.now();

    public static Result of(HttpStatus status, String message) {
        Result result = new Result();
        result.setStatus(status);
        result.setMessage(message);
        return result;
    }

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
