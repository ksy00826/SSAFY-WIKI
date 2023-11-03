package com.bdos.ssafywiki.util;

import jakarta.mail.internet.MimeMessage;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class EmailUtil {


    private final Logger log = LoggerFactory.getLogger(EmailUtil.class);
    private final JavaMailSender javaMailSender;
    private final RedisTemplate<String, String> redisTemplate;

    @Value("${spring.mail.username}")
    private String emailName;

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
        String randomNumber = makeRandomNumber();
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(emailName);
        message.setTo(email);
        message.setSubject("SSAFYWIKI 인증번호 입니다.");
        message.setText(randomNumber);
        javaMailSender.send(message);
        return 1;
    }

    public int authEmail(String email, String authCode) {
        ValueOperations<String, String> vop = redisTemplate.opsForValue();
        if(vop.get(email).equals(authCode)){
            return 1;
        }


        return 0;
    }
}
