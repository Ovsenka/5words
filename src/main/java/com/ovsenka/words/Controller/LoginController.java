package com.ovsenka.words.Controller;

import com.ovsenka.words.Repository.BotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/login")
public class LoginController {

    private AuthenticationManager authenticationManager;
    private BotRepository botRepository;

    @Autowired
    public LoginController(AuthenticationManager authenticationManager,
                           BotRepository botRepository) {
        this.authenticationManager = authenticationManager;
        this.botRepository = botRepository;
    }

    @GetMapping
    public String getLogin(@RequestParam(value = "error", defaultValue = "false") boolean loginError) {
        if (SecurityContextHolder.getContext().getAuthentication() != null)
            return "redirect:/admin/select";
        return "login";
    }
}
