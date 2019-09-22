# Mail Sender
[![Build Status](https://www.travis-ci.org/jiangtj/mail-sender.svg?branch=master)](https://www.travis-ci.org/jiangtj/mail-sender)
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/0ade707e15e4443fa5ec79eaa0e0145d)](https://www.codacy.com/manual/116749895/mail-sender?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=jiangtj/mail-sender&amp;utm_campaign=Badge_Grade)
[![Codacy Badge](https://api.codacy.com/project/badge/Coverage/0ade707e15e4443fa5ec79eaa0e0145d)](https://www.codacy.com/manual/116749895/mail-sender?utm_source=github.com&utm_medium=referral&utm_content=jiangtj/mail-sender&utm_campaign=Badge_Coverage)

轻量的邮件发送服务（正在积极开发中）

## Design

![image](https://user-images.githubusercontent.com/15902347/64757270-2b374880-d564-11e9-9a36-d319f7aad1b1.png)

修改：

1. 使用freemarker代替thymeleaf：thymeleaf与web容器强绑定，不是很优雅

## How to use

你需要提供一份邮箱的配置，详细见[MailProperties](https://github.com/spring-projects/spring-boot/blob/v2.1.8.RELEASE/spring-boot-project/spring-boot-autoconfigure/src/main/java/org/springframework/boot/autoconfigure/mail/MailProperties.java)

例如：添加application-mail.properties

```properties
spring.mail.host=smtp.office365.com
spring.mail.port=587
spring.mail.username=******
spring.mail.password=******
spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.starttls.enable=true
spring.mail.properties.mail.smtp.starttls.required=true
```

生成文档
```shell script
mvn test && mvn generate-resources
```

## Reason

在Spring文档中也提到了（虽然决定开发前未注意到）

6.2.2. Creating Email Content by Using a Templating Library

The code in the examples shown in the previous sections explicitly created the content of the email message, by using methods calls such as message.setText(..). This is fine for simple cases, and it is okay in the context of the aforementioned examples, where the intent was to show you the very basics of the API.

In your typical enterprise application, though, developers often do not create the content of email messages by using the previously shown approach for a number of reasons:

Creating HTML-based email content in Java code is tedious and error prone.

There is no clear separation between display logic and business logic.

Changing the display structure of the email content requires writing Java code, recompiling, redeploying, and so on.

Typically, the approach taken to address these issues is to use a template library (such as FreeMarker) to define the display structure of email content. This leaves your code tasked only with creating the data that is to be rendered in the email template and sending the email. It is definitely a best practice when the content of your email messages becomes even moderately complex, and, with the Spring Framework’s support classes for FreeMarker, it becomes quite easy to do.
