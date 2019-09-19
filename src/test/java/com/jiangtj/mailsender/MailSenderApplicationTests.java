package com.jiangtj.mailsender;

import com.jiangtj.mailsender.dto.SendDto;
import com.jiangtj.mailsender.dto.TemplateDto;
import com.jiangtj.mailsender.model.Record;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static org.springframework.restdocs.webtestclient.WebTestClientRestDocumentation.document;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureWebTestClient
@AutoConfigureRestDocs
public class MailSenderApplicationTests {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    public void contextLoads() {
        webTestClient.get().uri("/")
                .exchange()
                .expectBody(String.class)
                .isEqualTo("hi hi!")
                .consumeWith(document("run"));
    }

    @Test
    public void send() {
        Mono<SendDto> body = Mono.just(new TemplateDto())
                .doOnNext(templateDto -> templateDto.setName("simple"))
                .map(templateDto -> {
                    SendDto sendDto = new SendDto();
                    sendDto.setRender("md");
                    sendDto.setContent("test *YYYY*, [my blog](https://www.dnocm.com)");
                    sendDto.setTemplate(templateDto);
                    return sendDto;
                });
        webTestClient.post().uri("/send")
                .body(body, SendDto.class)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Record.class)
                .consumeWith(document("send"));
    }

}
