package com.jiangtj.mailsender.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by jiang (jiang.taojie@foxmail.com)
 * 2019/9/12 21:18 End.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SendRequestBody {
    private String to;
    private String subject;
    private String render;
    private String content;
    private TemplateDto template;
}
