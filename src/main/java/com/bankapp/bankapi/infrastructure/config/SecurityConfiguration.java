package com.bankapp.bankapi.infrastructure.config;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

  @Bean
  public SecurityFilterChain securityFilterChain(
    HttpSecurity http,
    @Value("${api.security.token}") String expectedToken
  ) throws Exception {
    if (expectedToken.isBlank()) {
      throw new IllegalStateException("A variável API_TOKEN deve conter um token não vazio.");
    }

    http
      .csrf(csrf -> csrf.disable())
      .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
      .authorizeHttpRequests(authorize -> authorize
        .requestMatchers("/swagger-ui/**", "/swagger-ui.html", "/v3/api-docs/**", "/h2-console/**").permitAll()
        .anyRequest().authenticated())
      .exceptionHandling(exceptions -> exceptions
        .authenticationEntryPoint((request, response, exception) ->
          response.sendError(HttpServletResponse.SC_UNAUTHORIZED)))
      .addFilterBefore(new ApiTokenFilter(expectedToken), UsernamePasswordAuthenticationFilter.class)
      .headers(headers -> headers.frameOptions(frame -> frame.sameOrigin()));

    return http.build();
  }
}
