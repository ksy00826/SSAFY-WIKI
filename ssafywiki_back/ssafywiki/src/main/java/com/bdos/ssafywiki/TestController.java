package com.bdos.ssafywiki;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TestController {

    private final RedisTemplate<String, String> redisTemplate;
    @GetMapping("/api/test")
    public String testGet() {
        return "Hello, world!";
    }

    @PostMapping("api/data/{key}/{value}")
    public ResponseEntity<String> setRedisData(@PathVariable("key") String key , @PathVariable("value") String value) throws Exception{

        redisTemplate.opsForValue().set(key, value );

        return new ResponseEntity<>("정상 등록", HttpStatus.CREATED);
    }

    @GetMapping("api/data/{key}")
    public ResponseEntity<String> getRedisData(
            @PathVariable("key") String key){

        return new ResponseEntity<>(redisTemplate.opsForValue().get(key), HttpStatus.OK);

    }
}

