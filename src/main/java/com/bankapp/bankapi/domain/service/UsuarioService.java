package com.bankapp.bankapi.domain.service;

import com.bankapp.bankapi.domain.entity.Usuario;
import com.bankapp.bankapi.domain.model.Paginacao;
import com.bankapp.bankapi.domain.ports.inbound.UsuarioUseCase;
import com.bankapp.bankapi.domain.ports.outbound.UsuarioRepositoryPort;
import com.bankapp.bankapi.infrastructure.adapters.inbound.dto.UsuarioRequestDTO;
import jakarta.validation.Valid;

import java.math.BigDecimal;

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
  public Paginacao<Usuario> listarUsuarios(int pagina, int tamanho) {
    if (pagina < 0) {
      throw new IllegalArgumentException("A página deve ser maior ou igual a zero");
    }
    if (tamanho < 1 || tamanho > 100) {
      throw new IllegalArgumentException("O tamanho da página deve estar entre 1 e 100");
    }
    return usuarioRepositoryPort.listar(pagina, tamanho);
  }

  @Override
  public Usuario listaUsuario(Long usuarioId) {
    return usuarioRepositoryPort.buscarPorId(usuarioId)
      .orElseThrow(() -> new RuntimeException("Usuário não encontrado" + usuarioId));
  }

  @Override
  public Usuario editarUsuario(Long usuarioId, Usuario usuario) {
    Usuario usuarioAtual = usuarioRepositoryPort.buscarPorId(usuarioId)
      .orElseThrow(() -> new RuntimeException("Usuário não encontrado!"));

    usuarioAtual.setNome(usuario.getNome());
    usuarioAtual.setCpf(usuario.getCpf());

    return usuarioRepositoryPort.salvar(usuarioAtual);
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

    if (valor.compareTo(usuario.getSaldo()) > 0) {
      throw new IllegalArgumentException("Saldo insuficiente");
    }

    BigDecimal subtrairSaldo = usuario.getSaldo().subtract(valor);

    usuario.setSaldo(subtrairSaldo);
    return usuarioRepositoryPort.salvar(usuario);
  }


}
