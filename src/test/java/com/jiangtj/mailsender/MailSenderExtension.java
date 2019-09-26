package com.jiangtj.mailsender;

import com.jiangtj.mailsender.dto.Result;
import com.jiangtj.mailsender.hander.SenderHandler;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.mockito.Mockito;
import org.springframework.context.ApplicationContext;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.mail.Address;
import javax.mail.internet.MimeMessage;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;

/**
 * Created by MrTT (jiang.taojie@foxmail.com)
 * 2019/9/26.
 */
@Slf4j
public class MailSenderExtension implements BeforeAllCallback {

    @Override
    public void beforeAll(ExtensionContext context) throws Exception {
        mockMailSender(context);
    }

    private void mockMailSender(ExtensionContext context) {
        log.info("Mock mail sender, just because don't send mail to my mail address!");
        ApplicationContext applicationContext = SpringExtension.getApplicationContext(context);
        JavaMailSender mailSender = applicationContext.getBean(JavaMailSender.class);
        SenderHandler senderHandler = applicationContext.getBean(SenderHandler.class);
        JavaMailSender mailSenderMock = Mockito.mock(JavaMailSender.class);
        doAnswer(invocation -> {
            MimeMessage argument = invocation.getArgument(0);
            log.info("Sending ...");
            log.info("Subject: {}", argument.getSubject());
            log.info("ReplyTo: {}", Stream.of(argument.getReplyTo()).map(Address::toString).collect(Collectors.joining(",")));
            log.info("Sender : {}", argument.getSender());
            log.info("Content: {}", argument.getContent());
            log.info("Send end.");
            if ("failure mail".equals(argument.getSubject())) {
                throw new SenderException(Result.serverError("This a failure mail error form mock bean"));
            }
            return "okay";
        }).when(mailSenderMock).send((MimeMessage)any());
        when(mailSenderMock.createMimeMessage()).thenReturn(mailSender.createMimeMessage());
        senderHandler.setMailSender(mailSenderMock);
    }

}
