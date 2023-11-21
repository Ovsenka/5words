package com.ovsenka.words.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthController {

    @GetMapping("/auth")
    public String auth(@RequestParam(value = "token") String token){
        //
        //
        return "index.html";
    }
}
