package com.bankapp.bankapi.domain.ports.inbound;

import com.bankapp.bankapi.domain.entity.Usuario;
import com.bankapp.bankapi.domain.model.Paginacao;

import java.math.BigDecimal;

public interface UsuarioUseCase {
  Usuario criarUsuario(Usuario usuario);
  void removerUsuario(String id);
  Paginacao<Usuario> listarUsuarios(int pagina, int tamanho);
  Usuario listaUsuario(String id);
  Usuario editarUsuario(String usuarioId, Usuario usuario);
  Usuario realizarDeposito(String usuarioId, BigDecimal valor);
  Usuario realizarSaque(String usuarioId, BigDecimal valor);
}
