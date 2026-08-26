package com.bankapp.bankapi.infrastructure.adapters.inbound.dto;

import java.math.BigDecimal;

public record UsuarioRequestDTO(
  String nome,
  String cpf,
  BigDecimal saldo
) {
}
