package com.ovsenka.words.Controller;

import com.ovsenka.words.Record.GenerateTokenResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicLong;

@RestController
public class GenerateTokenController {
    private final AtomicLong counter = new AtomicLong();
    @PostMapping("/generate/token")
    public GenerateTokenResponse generateToken(
            @RequestParam(value = "user_id") String userId
    ){
        System.out.println("id:"+userId);
        return new GenerateTokenResponse(counter.incrementAndGet(), "TEST_TOKEN");
    }
}
