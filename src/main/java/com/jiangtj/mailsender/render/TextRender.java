package com.jiangtj.mailsender.render;

import org.springframework.stereotype.Service;

/**
 * 不处理，直接返回
 */
@Service
public class TextRender implements Render {

    @Override
    public String render(String content) {
        return content;
    }

}
