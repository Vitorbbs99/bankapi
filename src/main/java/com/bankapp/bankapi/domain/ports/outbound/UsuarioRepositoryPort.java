package com.bankapp.bankapi.domain.ports.outbound;

import com.bankapp.bankapi.domain.entity.Usuario;
import com.bankapp.bankapi.domain.model.Pagina;

import java.util.Optional;

public interface UsuarioRepositoryPort {
  Usuario salvar(Usuario usuario);
  void remover(Long id);
  Pagina<Usuario> listar(int pagina, int tamanho);
  Optional<Usuario> buscarPorId(Long id);
}
