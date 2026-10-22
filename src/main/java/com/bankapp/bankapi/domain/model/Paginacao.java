package com.bankapp.bankapi.domain.model;

import java.util.List;

public record Paginacao<T>(
  List<T> conteudo,
  int pagina,
  int tamanho,
  long totalElementos,
  int totalPaginas
) {
  public Paginacao {
    conteudo = List.copyOf(conteudo);
  }
}
