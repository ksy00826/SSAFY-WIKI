package com.bdos.ssafywiki.configuration;


import com.bdos.ssafywiki.configuration.jwt.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.CorsConfigurer;
import org.springframework.security.config.annotation.web.configurers.HttpBasicConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;


import java.util.Arrays;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfig {
    /* * At the application startup, during configuration, spring security will try to look for a bean of type SecurityFilterChain
     * this bean is responsible for configuring all the HTTP security of our application
     */

    //    private final JwtTokenProvider jwtTokenProvider;
    private final JwtFilter jwtAuthenticationFilter;
    private final AuthenticationProvider authenticationProvider;
    private final JwtAuthenticationEntryPoint unauthorizedEntryPoint;
    private final JwtAccessDeniedHandler accessDeniedHandler;

    private final CorsConfig corsConfig;
    private static final Long MAX_AGE = 3600L;
    private static final int CORS_FILTER_ORDER = -102;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, HandlerMappingIntrospector introspector) throws Exception {
        MvcRequestMatcher.Builder mvcMatcherBuilder = new MvcRequestMatcher.Builder(introspector);

        http
                .httpBasic(HttpBasicConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .cors(AbstractHttpConfigurer::disable)
                .sessionManagement(manager -> manager.sessionCreationPolicy(STATELESS));
        http
                .authorizeHttpRequests((req) -> req
                                .requestMatchers(mvcMatcherBuilder.pattern("/api/docs/**")).permitAll()
                                .requestMatchers(mvcMatcherBuilder.pattern("/api/members/signup")).permitAll()
                                .requestMatchers(mvcMatcherBuilder.pattern("/api/members/login")).permitAll()
                                .requestMatchers(mvcMatcherBuilder.pattern("/api/members/email")).permitAll()
                                .requestMatchers(mvcMatcherBuilder.pattern("/api/members/refresh")).permitAll()
                                .requestMatchers(mvcMatcherBuilder.pattern("/api/chatlist/**")).permitAll()
                                .requestMatchers(mvcMatcherBuilder.pattern("/h2-console/**")).permitAll()
                                .requestMatchers(mvcMatcherBuilder.pattern("/favicon.ico")).permitAll()
                                .requestMatchers(mvcMatcherBuilder.pattern("/error")).permitAll()
                                .requestMatchers(mvcMatcherBuilder.pattern("/swagger-ui/**")).permitAll()
                                .requestMatchers(mvcMatcherBuilder.pattern("/swagger-resources/**")).permitAll()
                                .requestMatchers(mvcMatcherBuilder.pattern("/v3/api-docs/**")).permitAll()
                                .requestMatchers(mvcMatcherBuilder.pattern("/image/**")).permitAll()
                                .requestMatchers(mvcMatcherBuilder.pattern("/api/diary")).permitAll()
                                .requestMatchers(mvcMatcherBuilder.pattern("/ws")).permitAll()
//                                .anyRequest().authenticated()
                        //.requestMatchers("/api/v1/resource").hasAnyRole("ADMIN","USER") replaced with annotation in AuthorizationController
//                                .requestMatchers(HttpMethod.POST, "/api/user").hasRole("USER")
//                                .requestMatchers(HttpMethod.POST, "/admin").hasRole("ADMIN")
//                                .anyRequest().authenticated())
                )

                .exceptionHandling(exception -> exception
                        .authenticationEntryPoint(unauthorizedEntryPoint)
                        .accessDeniedHandler(accessDeniedHandler)
                )
                .authenticationProvider(authenticationProvider).addFilterBefore(
                        jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

}
