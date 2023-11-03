package com.bdos.ssafywiki.configuration;


import com.bdos.ssafywiki.configuration.jwt.*;
import com.bdos.ssafywiki.user.enums.Role;
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
import org.springframework.security.web.server.authentication.logout.DelegatingServerLogoutHandler;
import org.springframework.security.web.server.authentication.logout.SecurityContextServerLogoutHandler;
import org.springframework.security.web.server.authentication.logout.WebSessionServerLogoutHandler;
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


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, HandlerMappingIntrospector introspector) throws Exception {
        MvcRequestMatcher.Builder mvcMatcherBuilder = new MvcRequestMatcher.Builder(introspector);

        http
                .httpBasic(HttpBasicConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .cors(AbstractHttpConfigurer::disable)
                .sessionManagement(manager -> manager.sessionCreationPolicy(STATELESS));
        http
                .authenticationProvider(authenticationProvider).addFilterBefore(
                        jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests((req) -> req
                        // 인증이 필요 없는 곳
                        /* 유저 */
                        .requestMatchers(mvcMatcherBuilder.pattern("/api/members/**")).permitAll()
                        /* 문서 */
                        .requestMatchers(mvcMatcherBuilder.pattern("/api/docs/**")).permitAll()
                        .requestMatchers(mvcMatcherBuilder.pattern("/api/version/**")).permitAll()

                        /* swagger v3 */
                        .requestMatchers(mvcMatcherBuilder.pattern("/swagger-ui/**")).permitAll()
                        .requestMatchers(mvcMatcherBuilder.pattern("/swagger-resources/**")).permitAll()
                        .requestMatchers(mvcMatcherBuilder.pattern("/v3/api-docs/**")).permitAll()
                        /* 기타 */
                        .requestMatchers(mvcMatcherBuilder.pattern("/image/**")).permitAll()
                        .requestMatchers(mvcMatcherBuilder.pattern("/favicon.ico")).permitAll()
                        .requestMatchers(mvcMatcherBuilder.pattern("/error")).permitAll()

                )
                .authorizeHttpRequests((req) -> req
                        // 권한이 필요한 곳
                        /* 관리자 */
                        .requestMatchers(mvcMatcherBuilder.pattern("/api/admin/**")).hasRole(Role.ADMIN.getKey())

                )
                .authorizeHttpRequests((req) -> req
                        // 나머지는 인증이 필요한 곳
                        .anyRequest().permitAll()

                )
                .exceptionHandling(exception -> exception
                        .authenticationEntryPoint(unauthorizedEntryPoint)
                        .accessDeniedHandler(accessDeniedHandler)
                );
        return http.build();
    }

    DelegatingServerLogoutHandler mylogoutHandler = new DelegatingServerLogoutHandler(
            new WebSessionServerLogoutHandler(), new SecurityContextServerLogoutHandler()
    );

}
