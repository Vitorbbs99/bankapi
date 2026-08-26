package com.bankapp.bankapi.infrastructure.adapters.outbound;

import com.bankapp.bankapi.domain.model.Usuario;
import com.bankapp.bankapi.domain.ports.outbound.UsuarioRepositoryPort;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class UsuarioH2Adapter implements UsuarioRepositoryPort {

  private final SpringDataUsuarioRepository springDataUsuarioRepository;

  public UsuarioH2Adapter(SpringDataUsuarioRepository springDataUsuarioRepository) {
    this.springDataUsuarioRepository = springDataUsuarioRepository;
  }

  @Override
  public Usuario salvar(Usuario usuario) {
    UsuarioEntity entity = new UsuarioEntity(
      usuario.getId(),
      usuario.getNome(),
      usuario.getCpf(),
      usuario.getSaldo()
    );
    UsuarioEntity saved = springDataUsuarioRepository.save(entity);
    return new Usuario(saved.getId(), saved.getNome(), saved.getCpf(), saved.getSaldo());
  }

  @Override
  public void remover(Long usuarioId) {
    UsuarioEntity removerUsuario = springDataUsuarioRepository.findById(usuarioId)
        .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
    springDataUsuarioRepository.delete(removerUsuario);
    return;
  }

  @Override
  public List<Usuario> listar() {
    return springDataUsuarioRepository.findAll(Sort.by("id"))
      .stream()
      .map(usuario -> new Usuario(
        usuario.getId(),
        usuario.getNome(),
        usuario.getCpf(),
        usuario.getSaldo()
      ))
      .toList();
  }

  @Override
  public Optional<Usuario> buscarPorId(Long usuarioId) {
    return springDataUsuarioRepository.findById(usuarioId)
      .map(e -> new Usuario(e.getId(), e.getNome(), e.getCpf(), e.getSaldo()));
  }

}
