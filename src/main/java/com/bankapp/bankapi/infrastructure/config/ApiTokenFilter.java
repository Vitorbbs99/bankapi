package com.bankapp.bankapi.infrastructure.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

public class ApiTokenFilter extends OncePerRequestFilter {

  private static final String BEARER_PREFIX = "Bearer ";
  private final byte[] expectedToken;

  public ApiTokenFilter(String expectedToken) {
    this.expectedToken = expectedToken.getBytes(StandardCharsets.UTF_8);
  }

  @Override
  protected void doFilterInternal(
    HttpServletRequest request,
    HttpServletResponse response,
    FilterChain filterChain
  ) throws ServletException, IOException {
    String authorization = request.getHeader("Authorization");

    if (authorization != null && authorization.startsWith(BEARER_PREFIX)) {
      byte[] providedToken = authorization.substring(BEARER_PREFIX.length()).getBytes(StandardCharsets.UTF_8);
      if (MessageDigest.isEqual(expectedToken, providedToken)) {
        var authentication = new UsernamePasswordAuthenticationToken(
          "api-token", null, AuthorityUtils.NO_AUTHORITIES);
        SecurityContextHolder.getContext().setAuthentication(authentication);
      }
    }

    filterChain.doFilter(request, response);
  }
}
