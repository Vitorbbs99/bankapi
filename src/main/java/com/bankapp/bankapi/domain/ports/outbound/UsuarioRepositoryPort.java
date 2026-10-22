package com.bankapp.bankapi.domain.ports.outbound;

import com.bankapp.bankapi.domain.entity.Usuario;
import com.bankapp.bankapi.domain.model.Paginacao;

import java.util.Optional;

public interface UsuarioRepositoryPort {
  Usuario salvar(Usuario usuario);
  void remover(Long id);
  Paginacao<Usuario> listar(int pagina, int tamanho);
  Optional<Usuario> buscarPorId(Long id);
}
