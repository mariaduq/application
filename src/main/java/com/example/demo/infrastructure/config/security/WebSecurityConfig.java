package com.example.demo.infrastructure.config.security;

import com.example.demo.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    String[] resources = new String[] {
            "/include/**", "/css/**", "/icons/**", "/img/**", "/templates**"
    };

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests()
                .requestMatchers(resources).permitAll()
                .requestMatchers("/application/v1/", "/application/v1/homepage", "/application/v1/signup",
                        "/application/v1/login/forgotPassword").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                    .loginPage("/application/v1/login")
                    .permitAll()
                    .defaultSuccessUrl("/application/v1/loggedUser")
                    .failureUrl("/application/v1/login?error=true")
                    .usernameParameter("email")
                    .passwordParameter("password")
                    .and()
                .logout()
                .deleteCookies("JSESSIONID")
                    .logoutUrl("/application/v1/login?logout")
                    .logoutSuccessUrl("/application/v1/login?logout");

        return http.build();
    }

    BCryptPasswordEncoder bCryptPasswordEncoder;

    public BCryptPasswordEncoder passwordEncoder() {
        bCryptPasswordEncoder = new BCryptPasswordEncoder(4);
        return bCryptPasswordEncoder;
    }

    @Autowired
    UserDetailsServiceImpl userDetailsService;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

}
