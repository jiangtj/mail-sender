package com.jiangtj.mailsender;

import com.jiangtj.mailsender.dto.Result;
import lombok.Data;

/**
 * Created by MrTT (jiang.taojie@foxmail.com)
 * 2019/9/19.
 */
@Data
public class SenderException extends RuntimeException {

    private Result result;

    public SenderException(Result result) {
        super(result.getStatus().toString() + " :" + result.getMessage());
        this.result = result;
    }

}
