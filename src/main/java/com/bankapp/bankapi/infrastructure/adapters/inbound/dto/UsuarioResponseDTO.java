package com.bankapp.bankapi.infrastructure.adapters.inbound.dto;

import com.bankapp.bankapi.domain.model.Usuario;

import java.math.BigDecimal;

public record UsuarioResponseDTO (
  Long id,
  String nome,
  String cpf,
  BigDecimal saldo
){
  public static UsuarioResponseDTO doDominio (Usuario usuario) {
    return new UsuarioResponseDTO(
      usuario.getId(),
      usuario.getNome(),
      usuario.getCpf(),
      usuario.getSaldo()
    );
  }
}
