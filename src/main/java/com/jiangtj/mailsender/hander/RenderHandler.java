package com.jiangtj.mailsender.hander;

import com.jiangtj.mailsender.KeyValue;
import com.jiangtj.mailsender.render.Render;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by jiang (jiang.taojie@foxmail.com)
 * 2019/9/12 20:37 End.
 */
@Slf4j
public class RenderHandler {

    @Getter
    private Map<String, Render> renders;

    public RenderHandler(List<Render> renders){
        this.renders = renders.stream()
                .flatMap(item -> {
                    List<String> names = getName(item);
                    return names.stream()
                            .map(name -> KeyValue.of(name, item));
                })
                .collect(Collectors.toMap(
                        KeyValue::getKey,
                        KeyValue::getValue
                ));
    }

    private List<String> getName(Render render) {
        List<String> names = render.name();
        if (CollectionUtils.isEmpty(names)) {
            String name = render.getClass().getSimpleName();
            return Collections.singletonList(name.replace("Render", "").toLowerCase());
        }
        return names;
    }

    public String render(String name, String content) {
        Render render = renders.getOrDefault(name, null);
        if (render == null) {
            return content;
        }
        return render.render(content);
    }

}
