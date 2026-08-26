package com.bankapp.bankapi.infrastructure.adapters.inbound.dto;

import java.math.BigDecimal;

public record DepositoRequestDTO(
  BigDecimal valor
) {
}
