package com.website.website.configurations;

import com.website.website.service.CustomSuccessHandler;
import com.website.website.service.CustomUserDetailService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Set;


@Configuration
@EnableWebSecurity
public class SecurityConfig{
    @Autowired
    CustomSuccessHandler customSuccessHandler;

    @Autowired
    CustomUserDetailService customUserDetailsService;

    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @SuppressWarnings("removal")
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http)throws Exception {

            http.csrf().disable().authorizeHttpRequests()
                    .requestMatchers("/index", "/register", "/reset-password", "/password-request").permitAll()
                    .requestMatchers("/quiz").authenticated()
                    .requestMatchers("/home").permitAll().anyRequest().authenticated()
                    .and()
                    .formLogin()
                    .loginPage("/login")
                    .loginProcessingUrl("/login")
                    .defaultSuccessUrl("/home", true).permitAll()
                    //.successHandler(successHandler)
                    .permitAll()
                    .and()
                    .logout()
                    .invalidateHttpSession(true)
                    .clearAuthentication(true)
                    .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                    .logoutSuccessUrl("/login?logout").permitAll();

            return http.build();

        }

        @Autowired
        public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
            auth.userDetailsService(customUserDetailsService).passwordEncoder(passwordEncoder());
        }
//@Component
//public static class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
//
//
//    @Override
//    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
//                                        Authentication authentication) throws IOException, ServletException {
//        Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());
//        if (roles.contains("ADMIN")) {
//            response.sendRedirect("/admin");
//
//        } else {
//            response.sendRedirect("/home");
//        }
//    }
//}
}