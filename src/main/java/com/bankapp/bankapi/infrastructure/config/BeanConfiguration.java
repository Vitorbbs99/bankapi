package com.bankapp.bankapi.infrastructure.config;

import com.bankapp.bankapi.domain.ports.outbound.UsuarioRepositoryPort;
import com.bankapp.bankapi.domain.service.UsuarioService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {

  @Bean
  public UsuarioService usuarioService(UsuarioRepositoryPort repositoryPort) {
    return new UsuarioService(repositoryPort);
  }
}
