package com.bankapp.bankapi.domain.ports.outbound;

import com.bankapp.bankapi.domain.model.Usuario;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepositoryPort {
  Usuario salvar(Usuario usuario);
  void remover(Long id);
  List<Usuario> listar();
  Optional<Usuario> buscarPorId(Long id);
}
