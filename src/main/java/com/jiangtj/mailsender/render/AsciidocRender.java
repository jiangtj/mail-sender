package com.jiangtj.mailsender.render;

import org.asciidoctor.Asciidoctor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * Created by jiang (jiang.taojie@foxmail.com)
 * 2019/9/12 22:19 End.
 */
@Service
public class AsciidocRender implements Render {

    private Asciidoctor asciidoctor = Asciidoctor.Factory.create();

    @Override
    public String render(String content) {
        return asciidoctor.convert(content, new HashMap<>());
    }

    @Override
    public List<String> name() {
        return Arrays.asList("adoc", "asciidoc");
    }

}
