# Mail Sender

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
