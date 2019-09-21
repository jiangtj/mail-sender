package com.jiangtj.mailsender.dto;

import com.jiangtj.mailsender.model.Record;
import lombok.Data;

/**
 * Created by jiang (jiang.taojie@foxmail.com)
 * 2019/9/21 23:18 End.
 */
@Data
public class SendStream {
    private SendRequestBody requestBody;
    private TemplateDto template;
    private Record record;
    private String html;
}
