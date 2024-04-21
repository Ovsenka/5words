package com.ovsenka.words.Security;


import com.ovsenka.words.Entity.BotEntity;
import com.ovsenka.words.Repository.BotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.AnonymousConfigurer;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import java.util.Collections;


@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private BotRepository botRepository;

    @Autowired
    public SecurityConfig(BotRepository botRepository) {
        this.botRepository = botRepository;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(
                        auth -> auth
                                .requestMatchers(HttpMethod.GET, "/admin/**").authenticated()
                                .requestMatchers(HttpMethod.POST, "/admin/**").authenticated()
                                .anyRequest().permitAll()

                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .loginProcessingUrl("/login")
                        .defaultSuccessUrl("/admin/select")
                        .permitAll()
                        .failureUrl("/login?error=true")
                )
                .anonymous(AnonymousConfigurer::disable)
                .authenticationProvider(authenticationProvider());
        return http.build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider(){
        final DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService());
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }
    @Bean
    public PasswordEncoder passwordEncoder(){
        return NoOpPasswordEncoder.getInstance();
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration authenticationConfiguration
    ) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
    @Bean
    public UserDetailsService userDetailsService(){
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                BotEntity bot = botRepository.findByToken(username).orElseThrow(() -> new UsernameNotFoundException("Username not found"));
                User user = new User(
                        bot.getToken(),
                        "null",
                        Collections.singleton(new SimpleGrantedAuthority("ROLE_ADMIN")));
                return user;
            }

            public UserDetails loadUserByToken(String password) throws UsernameNotFoundException {
                BotEntity bot = botRepository.findByToken(password).orElseThrow(() -> new UsernameNotFoundException("Token not found"));
                return new User(
                        String.valueOf(bot.getId()),
                        bot.getToken(),
                        Collections.singleton(new SimpleGrantedAuthority("ROLE_ADMIN")));
            }
        };
    }
}
