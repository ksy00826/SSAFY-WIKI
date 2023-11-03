package com.bdos.ssafywiki.configuration;

import java.util.Properties;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
public class MailConfig {
    @Value("${spring.mail.username}")
    private String emailName;
    @Value("${spring.mail.password}")
    private String emailPassword;
    @Value("${spring.mail.host}")
    private String emailHost;
    @Value("${spring.mail.port}")
    private String emailPort;
    @Bean
    public JavaMailSender javaMailService() {
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
        javaMailSender.setHost(emailHost);
        javaMailSender.setUsername(emailName);
        javaMailSender.setPassword(emailPassword);

        javaMailSender.setPort(Integer.parseInt(emailPort));

        javaMailSender.setJavaMailProperties(getMailProperties());

        return javaMailSender;
    }

    private Properties getMailProperties() {
        Properties properties = new Properties();
        properties.setProperty("mail.transport.protocol", "smtp");
        properties.setProperty("mail.smtp.auth", "true");
        properties.setProperty("mail.smtp.starttls.enable", "true");
        properties.setProperty("mail.debug", "true");
        properties.setProperty("mail.smtp.ssl.trust",emailHost);
        properties.setProperty("mail.smtp.ssl.enable","true");
        return properties;
    }
}
