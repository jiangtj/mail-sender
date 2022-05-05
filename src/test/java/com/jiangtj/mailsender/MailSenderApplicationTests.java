package com.jiangtj.mailsender;

import com.jiangtj.mailsender.dto.Result;
import com.jiangtj.mailsender.dto.SendRequestBody;
import com.jiangtj.mailsender.dto.TemplateDto;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Slf4j
@ApplicationTest
class MailSenderApplicationTests {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    void contextLoads() {
        webTestClient.get().uri("/")
                .exchange()
                .expectBody(String.class)
                .isEqualTo("Hello world!");
    }

    @Test
    void send() {
        TemplateDto template = new TemplateDto();
        template.setName("simple");
        template.setParams(new HashMap<>());
        SendRequestBody body =  SendRequestBody.builder()
                .to("jiangtjtest@outlook.com")
                .subject("Test Email!")
                .render("md")
                .content("test *YYYY*, [my blog](https://www.dnocm.com)")
                .template(template)
                .build();
        webTestClient.post().uri("/send")
                .bodyValue(body)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Result.class)
                .value(result -> assertEquals(result.getStatus(), HttpStatus.OK));
    }

    @Test
    void sendFail1() {
        SendRequestBody body =  SendRequestBody.builder()
                .to("jiangtjtest@outlook.com")
                .render("md")
                .content("test *YYYY*, [my blog](https://www.dnocm.com)")
                .build();
        webTestClient.post().uri("/send")
                .bodyValue(body)
                .exchange()
                .expectStatus().is4xxClientError()
                .expectBody(Result.class)
                .value(result -> {
                    assertEquals(result.getStatus(), HttpStatus.BAD_REQUEST);
                    log.error(result.toString());
                });
    }

    @Test
    void sendFail2() {
        SendRequestBody body =  SendRequestBody.builder()
                .to("jiangtjtest@outlook.com")
                .subject("failure mail")
                .render("md")
                .content("test *YYYY*, [my blog](https://www.dnocm.com)")
                .build();
        webTestClient.post().uri("/send")
                .bodyValue(body)
                .exchange()
                .expectStatus().is5xxServerError()
                .expectBody(Result.class)
                .value(result -> {
                    assertEquals(result.getStatus(), HttpStatus.INTERNAL_SERVER_ERROR);
                    log.error(result.toString());
                });
    }

}
