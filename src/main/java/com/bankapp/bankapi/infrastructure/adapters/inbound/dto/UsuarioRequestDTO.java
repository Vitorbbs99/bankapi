package com.bankapp.bankapi.infrastructure.adapters.inbound.dto;

import com.bankapp.bankapi.domain.entity.Usuario;

import java.math.BigDecimal;

public record UsuarioRequestDTO(
  String nome,
  String cpf,
  BigDecimal saldo
) {
  public Usuario paraDominio() {
    return new Usuario(null, this.nome, this.cpf, null);
  }
}
