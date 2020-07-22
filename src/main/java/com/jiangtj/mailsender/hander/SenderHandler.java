package com.jiangtj.mailsender.hander;

import com.jiangtj.mailsender.SenderException;
import com.jiangtj.mailsender.dto.DataExchange;
import com.jiangtj.mailsender.dto.Result;
import com.jiangtj.mailsender.dto.SendRequestBody;
import com.jiangtj.mailsender.dto.TemplateDto;
import com.jiangtj.mailsender.properties.SenderProperties;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by jiang (jiang.taojie@foxmail.com)
 * 2019/9/21 22:37 End.
 */
@Slf4j
public class SenderHandler {

    private SenderProperties properties;
    @Setter
    private RenderHandler renderHandler;
    @Setter
    private TemplateHandler templateHandler;
    @Setter
    private JavaMailSender mailSender;

    public SenderHandler(SenderProperties properties) {
        this.properties = properties;
    }

    /**
     * Send mail
     */
    public Mono<ServerResponse> send(ServerRequest request) {
        return request.bodyToMono(SendRequestBody.class)
                .map(this::handleParams)
                .doOnNext(this::renderContent)
                .doOnNext(this::handleTemplate)
                .doOnNext(this::sendMail)
                //.doOnNext(this::handleResult);
                .thenReturn(Result.ok())
                .transform(Result::transformToResponse);
    }

    /**
     * Handle params for set default value and transform to SendStream
     */
    private DataExchange handleParams(SendRequestBody body) {
        if (body.getTemplate() == null) {
            body.setTemplate(new TemplateDto());
        }
        TemplateDto template = body.getTemplate();
        if (template.getParams() == null) {
            template.setParams(new HashMap<>());
        }
        DataExchange exchange = new DataExchange();
        exchange.setRequestBody(body);
        return exchange;
    }

    /**
     * Use render engine render content.
     * It will set value to `body` and `bodyRaw` in template params.
     * So please don't use them.
     */
    private void renderContent(DataExchange exchange) {
        SendRequestBody body = exchange.getRequestBody();
        String render = body.getRender();
        String content = body.getContent();
        String renderHtml = renderHandler.render(render, content);
        exchange.setRenderedContent(renderHtml);
    }

    /**
     * Handle template and params, make them to html.
     */
    private void handleTemplate(DataExchange exchange) {
        SendRequestBody body = exchange.getRequestBody();
        TemplateDto template = body.getTemplate();
        Map<String, Object> params = template.getParams();
        params.put("bodyRaw", body.getContent());
        params.put("body", exchange.getRenderedContent());
        String html = templateHandler.handle(template.getName(), params);
        exchange.setHtml(html);
    }

    /**
     * Send mail to people with rendered html.
     */
    private void sendMail(DataExchange exchange) {
        SendRequestBody requestBody = exchange.getRequestBody();
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        try {
            helper.setTo(requestBody.getTo());
            helper.setSubject(requestBody.getSubject());
            helper.setText(exchange.getHtml(), true);
            helper.setFrom(properties.getMail().getUsername());
        } catch (MessagingException e) {
            log.error("Mail MessagingException", e);
            throw new SenderException(Result.serverError("Mail MessagingException!"));
        }
        mailSender.send(message);
    }

}
