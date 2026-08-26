package com.bankapp.bankapi.domain.ports.inbound;

import com.bankapp.bankapi.domain.model.Usuario;

import java.math.BigDecimal;
import java.util.List;

public interface UsuarioUseCase {
  Usuario criarUsuario(Usuario usuario);
  void removerUsuario(Long id);
  List<Usuario> listarUsuarios();
  Usuario listaUsuario(Long id);
  Usuario realizarDeposito(Long usuarioId, BigDecimal valor);
  Usuario realizarSaque(Long usuarioId, BigDecimal valor);
}
