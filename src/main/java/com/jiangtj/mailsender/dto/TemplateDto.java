package com.jiangtj.mailsender.dto;

import lombok.Data;

import java.util.Map;

/**
 * Created by MrTT (jiang.taojie@foxmail.com)
 * 2019/9/19.
 */
@Data
public class TemplateDto {
    private String name;
    private Map<String, Object> params;
}
