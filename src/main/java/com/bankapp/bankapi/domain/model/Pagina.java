package com.bankapp.bankapi.domain.model;

import java.util.List;

public record Pagina<T>(
  List<T> conteudo,
  int pagina,
  int tamanho,
  long totalElementos,
  int totalPaginas
) {
  public Pagina {
    conteudo = List.copyOf(conteudo);
  }
}
