package com.bdos.ssafywiki.util;

import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class EmailUtil {

    private final Logger log = LoggerFactory.getLogger(EmailUtil.class);
    private final RedisTemplate<String, String> redisTemplate;
    @Value("mail.username")
    private String emailName;
    @Value("mail.password")
    private String emailPassword;
    @Value("mail.host")
    private String emailHost;
    @Value("mail.port")
    private String emailPort;

    private String makeRandomNumber() {
        Random random = new Random();        //랜덤 함수 선언
        int createNum = 0;            //1자리 난수
        String ranNum = "";            //1자리 난수 형변환 변수
        int letter = 6;            //난수 자릿수:6
        String resultNum = "";        //결과 난수

        for (int i = 0; i < letter; i++) {

            createNum = random.nextInt(9);        //0부터 9까지 올 수 있는 1자리 난수 생성
            ranNum = Integer.toString(createNum);  //1자리 난수를 String으로 형변환
            resultNum += ranNum;            //생성된 난수(문자열)을 원하는 수(letter)만큼 더하며 나열
        }
        return resultNum;
    }

    public int sendEmail(String email) {
        Properties prop = new Properties();

        prop.put("mail.smtp.host", emailHost);
        prop.put("mail.smtp.port", emailPort);
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.ssl.enable", "true");
        prop.put("mail.smtp.ssl.trust", emailHost);

        Session session = Session.getDefaultInstance(prop, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(emailName, emailPassword);
            }
        });
        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(emailName));

            message.addRecipient(Message.RecipientType.TO, new InternetAddress(email)); //수신자메일주소
            message.setSubject("[SSAFY WIKI] 인증번호 전송");     //메일 제목을 입력
            //randomNumber to Redis
            String randomNumber = makeRandomNumber();
            ValueOperations<String, String> vop = redisTemplate.opsForValue();
            vop.set(email,randomNumber , 5 , TimeUnit.MINUTES);
//            redisTemplateString.opsForList().rightPush(email, randomNumber);
            message.setText("<h3>SSAFY WIKI 회원가입 인증번호 입니다.<h3>" + randomNumber);      //메일 내용을 입력

            // send the message
            Transport.send(message); ////전송
            log.info("메세지 전송 성공");
            return 1;
        } catch (AddressException e) {
            log.error(e.toString());
        } catch (MessagingException e) {
            log.error(e.toString());
        }
        return 0;
    }

    public int authEmail(String email, String authCode) {
        ValueOperations<String, String> vop = redisTemplate.opsForValue();
        if(vop.get(email).equals(authCode)){
            return 1;
        }


        return 0;
    }
}
