package com.ovsenka.words.Controller;

import com.ovsenka.words.Record.TokenResponse;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.atomic.AtomicLong;

@RestController
@RequestMapping("/api/v1/token")
public class TokenController {
    private final AtomicLong counter = new AtomicLong();

    @PostMapping("/generate")
    public TokenResponse generateToken(
            @RequestParam(value = "user_id") String userId,
            @RequestParam(value = "bot_token") String botToken
    ){
        System.out.println("id:"+userId);
        System.out.println("bot_token:"+botToken);
        if (botToken.equals("BOT_TOKEN_TWSADGDF") && userId.equals("12345"))
            return new TokenResponse(counter.incrementAndGet(), "ABCDEE$123213");
        return new TokenResponse(-1, "Bad Request");
    }
}
