package com.bankapp.bankapi.domain.service;

import com.bankapp.bankapi.domain.model.Usuario;
import com.bankapp.bankapi.domain.ports.inbound.UsuarioUseCase;
import com.bankapp.bankapi.domain.ports.outbound.UsuarioRepositoryPort;

import java.math.BigDecimal;
import java.util.List;

public class UsuarioService implements UsuarioUseCase {

  private final UsuarioRepositoryPort usuarioRepositoryPort;

  public UsuarioService(UsuarioRepositoryPort usuarioRepositoryPort) {
    this.usuarioRepositoryPort = usuarioRepositoryPort;
  }

  @Override
  public Usuario criarUsuario(Usuario usuario) {
    return usuarioRepositoryPort.salvar(usuario);
  }

  @Override
  public void removerUsuario(Long usuarioId) {
    Usuario usuario = usuarioRepositoryPort.buscarPorId(usuarioId)
      .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

    usuarioRepositoryPort.remover(usuario.getId());
  }

  @Override
  public List<Usuario> listarUsuarios() {
    return usuarioRepositoryPort.listar();
  }

  @Override
  public Usuario listaUsuario(Long usuarioId) {
    return usuarioRepositoryPort.buscarPorId(usuarioId)
      .orElseThrow(() -> new RuntimeException("Usuário não encontrado" + usuarioId));
  }

  @Override
  public Usuario realizarDeposito(Long usuarioId, BigDecimal valor) {
    Usuario usuario = usuarioRepositoryPort.buscarPorId(usuarioId)
      .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

    if (valor.compareTo(BigDecimal.ZERO) <= 0) {
      throw new IllegalArgumentException("Valor deve ser positivo");
    }

    BigDecimal somaDeposito = usuario.getSaldo().add(valor);

    usuario.setSaldo(somaDeposito);
    return usuarioRepositoryPort.salvar(usuario);
  }

  @Override
  public Usuario realizarSaque(Long id, BigDecimal valor) {
    Usuario usuario = usuarioRepositoryPort.buscarPorId(id)
      .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

    if (valor.compareTo(BigDecimal.ZERO) <= 0) {
      throw new IllegalArgumentException("Valor deve ser positivo");
    }

    BigDecimal subtrairSaldo = usuario.getSaldo().subtract(valor);

    usuario.setSaldo(subtrairSaldo);
    return usuarioRepositoryPort.salvar(usuario);
  }


}
