package com.jiangtj.mailsender.dto;

import lombok.Data;

/**
 * Created by jiang (jiang.taojie@foxmail.com)
 * 2019/9/21 23:18 End.
 */
@Data
public class DataExchange {
    private SendRequestBody requestBody;
    private String renderedContent;
    private String html;
}
