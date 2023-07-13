package com.example.demo.infraestructure.config.security;

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
                .anyRequest()
                .permitAll()
                .and()
                .formLogin()
                    .loginPage("/login")
                    .permitAll()
                    .defaultSuccessUrl("/loggedUser")
                    .failureUrl("/login?error=true")
                    .usernameParameter("email")
                    .passwordParameter("password")
                    .and()
                .logout()
                .deleteCookies("JSESSIONID")
                    .logoutUrl("/login?logout")
                    .logoutSuccessUrl("/login?logout");

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
