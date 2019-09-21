package com.jiangtj.mailsender.model;

import lombok.Data;

/**
 * Created by MrTT (jiang.taojie@foxmail.com)
 * 2019/9/19.
 */
@Data
public class Record {
    private String addressee;
    private String sender;
    private String title;
    private String content;
}
