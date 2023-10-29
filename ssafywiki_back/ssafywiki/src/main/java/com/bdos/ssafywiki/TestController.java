package com.bdos.ssafywiki;

import lombok.RequiredArgsConstructor;
import java.util.Map;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.data.redis.core.ValueOperations;

@RestController
@RequiredArgsConstructor
public class TestController {

    private final RedisTemplate<String, String> redisTemplate;
    @GetMapping("/api/test")
    public String testGet() {
        return "Hello, world!";
    }

    @PostMapping("api/data")
    public ResponseEntity<String> setRedisData(@RequestBody Map<String, String> req) throws Exception{

        ValueOperations<String, String> vop = redisTemplate.opsForValue();
    		try {
    			// Redis Set Key-value
    			vop.set(req.get("key").toString(), req.get("value").toString());
    			return "set message success";
    		} catch (Exception e) {
    			return "set message fail";
    		}

        return new ResponseEntity<>("정상 등록", HttpStatus.CREATED);
    }

    @GetMapping("api/data/{key}")
    public ResponseEntity<String> getRedisData(
            @PathVariable("key") String key){

        return new ResponseEntity<>(redisTemplate.opsForValue().get(key), HttpStatus.OK);

    }
}

