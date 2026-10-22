package com.bankapp.bankapi.domain.service;

import com.bankapp.bankapi.domain.entity.Usuario;
import com.bankapp.bankapi.domain.model.Paginacao;
import com.bankapp.bankapi.domain.ports.outbound.UsuarioRepositoryPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UsuarioServiceTest {

  private UsuarioRepositoryPort repository;
  private UsuarioService service;

  @BeforeEach
  void setUp() {
    repository = mock(UsuarioRepositoryPort.class);
    service = new UsuarioService(repository);
  }

  @Test
  void criarUsuarioDeveDelegarSalvamentoAoRepositorio() {
    Usuario usuario = usuario(1L, "Ana", "123", "50.00");
    when(repository.salvar(usuario)).thenReturn(usuario);

    Usuario resultado = service.criarUsuario(usuario);

    assertSame(usuario, resultado);
    verify(repository).salvar(usuario);
  }

  @Test
  void removerUsuarioDeveRemoverUsuarioEncontrado() {
    when(repository.buscarPorId(1L)).thenReturn(Optional.of(usuario(1L, "Ana", "123", "50.00")));

    service.removerUsuario(1L);

    verify(repository).remover(1L);
  }

  @Test
  void removerUsuarioDeveFalharQuandoUsuarioNaoExiste() {
    when(repository.buscarPorId(1L)).thenReturn(Optional.empty());

    assertThrows(RuntimeException.class, () -> service.removerUsuario(1L));

    verify(repository, never()).remover(anyLong());
  }

  @Test
  void listarUsuariosDeveDelegarComPaginacaoValida() {
    Paginacao<Usuario> pagina = new Paginacao<>(List.of(), 0, 10, 0, 0);
    when(repository.listar(0, 10)).thenReturn(pagina);

    assertSame(pagina, service.listarUsuarios(0, 10));
    verify(repository).listar(0, 10);
  }

  @Test
  void listarUsuariosDeveAceitarTamanhoMaximo() {
    Paginacao<Usuario> pagina = new Paginacao<>(List.of(), 2, 100, 0, 0);
    when(repository.listar(2, 100)).thenReturn(pagina);

    assertSame(pagina, service.listarUsuarios(2, 100));
  }

  @Test
  void listarUsuariosDeveRejeitarPaginaNegativa() {
    assertThrows(IllegalArgumentException.class, () -> service.listarUsuarios(-1, 10));
    verifyNoInteractions(repository);
  }

  @Test
  void listarUsuariosDeveRejeitarTamanhoMenorQueUm() {
    assertThrows(IllegalArgumentException.class, () -> service.listarUsuarios(0, 0));
    verifyNoInteractions(repository);
  }

  @Test
  void listarUsuariosDeveRejeitarTamanhoMaiorQueCem() {
    assertThrows(IllegalArgumentException.class, () -> service.listarUsuarios(0, 101));
    verifyNoInteractions(repository);
  }

  @Test
  void listaUsuarioDeveRetornarUsuarioEncontrado() {
    Usuario usuario = usuario(1L, "Ana", "123", "50.00");
    when(repository.buscarPorId(1L)).thenReturn(Optional.of(usuario));

    assertSame(usuario, service.listaUsuario(1L));
  }

  @Test
  void listaUsuarioDeveFalharQuandoUsuarioNaoExiste() {
    when(repository.buscarPorId(1L)).thenReturn(Optional.empty());

    assertThrows(RuntimeException.class, () -> service.listaUsuario(1L));
  }

  @Test
  void editarUsuarioDeveAtualizarNomeECpfPreservandoIdESaldo() {
    Usuario atual = usuario(1L, "Ana", "123", "50.00");
    Usuario alteracoes = usuario(99L, "Ana Souza", "456", "999.00");
    when(repository.buscarPorId(1L)).thenReturn(Optional.of(atual));
    when(repository.salvar(atual)).thenReturn(atual);

    Usuario resultado = service.editarUsuario(1L, alteracoes);

    assertSame(atual, resultado);
    assertEquals("Ana Souza", atual.getNome());
    assertEquals("456", atual.getCpf());
    assertEquals(1L, atual.getId());
    assertEquals(new BigDecimal("50.00"), atual.getSaldo());
    verify(repository).salvar(atual);
  }

  @Test
  void editarUsuarioDeveFalharQuandoUsuarioNaoExiste() {
    when(repository.buscarPorId(1L)).thenReturn(Optional.empty());

    assertThrows(RuntimeException.class,
      () -> service.editarUsuario(1L, usuario(1L, "Ana", "123", "0.00")));

    verify(repository, never()).salvar(any());
  }

  @Test
  void realizarDepositoDeveSomarValorAoSaldoESalvar() {
    Usuario usuario = usuario(1L, "Ana", "123", "50.00");
    when(repository.buscarPorId(1L)).thenReturn(Optional.of(usuario));
    when(repository.salvar(usuario)).thenReturn(usuario);

    Usuario resultado = service.realizarDeposito(1L, new BigDecimal("25.50"));

    assertSame(usuario, resultado);
    assertEquals(new BigDecimal("75.50"), usuario.getSaldo());
    verify(repository).salvar(usuario);
  }

  @Test
  void realizarDepositoDeveRejeitarValorZeroOuNegativo() {
    for (BigDecimal valor : List.of(BigDecimal.ZERO, new BigDecimal("-1.00"))) {
      Usuario usuario = usuario(1L, "Ana", "123", "50.00");
      when(repository.buscarPorId(1L)).thenReturn(Optional.of(usuario));

      assertThrows(IllegalArgumentException.class, () -> service.realizarDeposito(1L, valor));
      assertEquals(new BigDecimal("50.00"), usuario.getSaldo());
      verify(repository, never()).salvar(any());
    }
  }

  @Test
  void realizarDepositoDeveFalharQuandoUsuarioNaoExiste() {
    when(repository.buscarPorId(1L)).thenReturn(Optional.empty());

    assertThrows(RuntimeException.class,
      () -> service.realizarDeposito(1L, new BigDecimal("10.00")));
  }

  @Test
  void realizarSaqueDeveSubtrairValorDoSaldoESalvar() {
    Usuario usuario = usuario(1L, "Ana", "123", "50.00");
    when(repository.buscarPorId(1L)).thenReturn(Optional.of(usuario));
    when(repository.salvar(usuario)).thenReturn(usuario);

    Usuario resultado = service.realizarSaque(1L, new BigDecimal("20.00"));

    assertSame(usuario, resultado);
    assertEquals(new BigDecimal("30.00"), usuario.getSaldo());
    verify(repository).salvar(usuario);
  }

  @Test
  void realizarSaqueDeveRejeitarValorZeroOuNegativo() {
    for (BigDecimal valor : List.of(BigDecimal.ZERO, new BigDecimal("-1.00"))) {
      Usuario usuario = usuario(1L, "Ana", "123", "50.00");
      when(repository.buscarPorId(1L)).thenReturn(Optional.of(usuario));

      assertThrows(IllegalArgumentException.class, () -> service.realizarSaque(1L, valor));
      assertEquals(new BigDecimal("50.00"), usuario.getSaldo());
      verify(repository, never()).salvar(any());
    }
  }

  @Test
  void realizarSaqueDeveFalharQuandoUsuarioNaoExiste() {
    when(repository.buscarPorId(1L)).thenReturn(Optional.empty());

    assertThrows(RuntimeException.class,
      () -> service.realizarSaque(1L, new BigDecimal("10.00")));
  }

  private Usuario usuario(Long id, String nome, String cpf, String saldo) {
    return new Usuario(id, nome, cpf, new BigDecimal(saldo));
  }
}
