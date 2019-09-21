package com.jiangtj.mailsender.hander;

import com.jiangtj.mailsender.dto.SendRequestBody;
import com.jiangtj.mailsender.dto.SendStream;
import com.jiangtj.mailsender.dto.TemplateDto;
import com.jiangtj.mailsender.model.Record;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by jiang (jiang.taojie@foxmail.com)
 * 2019/9/21 22:37 End.
 */
@Slf4j
public class SenderHandler {

    private RenderHandler renderHandler;
    private TemplateHandler templateHandler;

    public SenderHandler(RenderHandler renderHandler, TemplateHandler templateHandler) {
        this.renderHandler = renderHandler;
        this.templateHandler = templateHandler;
    }

    /**
     * Send sms
     */
    public Mono<ServerResponse> send(ServerRequest request) {
        Mono<Record> result = request.bodyToMono(SendRequestBody.class)
                .map(this::handleParams)
                .doOnNext(this::renderContent)
                .doOnNext(this::handleTemplate)
                // Send mail
                /*.doOnNext(record -> {
                    //todo 发送邮件
                });*/
                .map(SendStream::getRecord);
        //.thenReturn("success!");
        return ServerResponse.ok().body(result, Record.class);
    }

    /**
     * Handle params for set default value and transform to SendStream
     */
    private SendStream handleParams(SendRequestBody body) {
        if (body.getTemplate() == null) {
            body.setTemplate(new TemplateDto());
        }
        TemplateDto template = body.getTemplate();
        if (template.getParams() == null) {
            template.setParams(new HashMap<>());
        }
        SendStream stream = new SendStream();
        stream.setRequestBody(body);
        stream.setTemplate(template);
        return stream;
    }

    /**
     * Use render engine render content.
     * It will set value to `body` and `bodyRaw` in template params.
     * So please don't use them.
     */
    private void renderContent(SendStream stream) {
        SendRequestBody body = stream.getRequestBody();
        String render = body.getRender();
        String content = body.getContent();
        String renderHtml = renderHandler.render(render, content);
        if (StringUtils.isEmpty(renderHtml)) {
            return;
        }
        Map<String, Object> params = stream.getTemplate().getParams();
        params.put("bodyRaw", content);
        params.put("body", renderHtml);
    }

    /**
     * Handle template and params, make them to html.
     */
    private void handleTemplate(SendStream stream) {
        TemplateDto template = stream.getTemplate();
        String html = templateHandler.handle(template.getName(), template.getParams());
        Record record = new Record();
        record.setContent(html);
        stream.setRecord(record);
    }

}
