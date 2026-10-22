package com.bankapp.bankapi.domain.ports.inbound;

import com.bankapp.bankapi.domain.entity.Usuario;
import com.bankapp.bankapi.domain.model.Pagina;
import com.bankapp.bankapi.infrastructure.adapters.inbound.dto.UsuarioRequestDTO;
import jakarta.validation.Valid;

import java.math.BigDecimal;

public interface UsuarioUseCase {
  Usuario criarUsuario(Usuario usuario);
  void removerUsuario(Long id);
  Pagina<Usuario> listarUsuarios(int pagina, int tamanho);
  Usuario listaUsuario(Long id);
  Usuario editarUsuario(Long usuarioId, Usuario usuario);
  Usuario realizarDeposito(Long usuarioId, BigDecimal valor);
  Usuario realizarSaque(Long usuarioId, BigDecimal valor);
}
