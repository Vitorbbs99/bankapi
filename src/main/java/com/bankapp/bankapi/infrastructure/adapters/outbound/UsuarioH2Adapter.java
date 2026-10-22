package com.bankapp.bankapi.infrastructure.adapters.outbound;

import com.bankapp.bankapi.domain.entity.Usuario;
import com.bankapp.bankapi.domain.model.Pagina;
import com.bankapp.bankapi.domain.ports.outbound.UsuarioRepositoryPort;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

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
  public Pagina<Usuario> listar(int pagina, int tamanho) {
    var resultado = springDataUsuarioRepository.findAll(PageRequest.of(pagina, tamanho, Sort.by("id")));
    List<Usuario> usuarios = resultado.getContent().stream()
      .map(usuario -> new Usuario(
        usuario.getId(),
        usuario.getNome(),
        usuario.getCpf(),
        usuario.getSaldo()
      ))
      .toList();
    return new Pagina<>(usuarios, resultado.getNumber(), resultado.getSize(),
      resultado.getTotalElements(), resultado.getTotalPages());
  }

  @Override
  public Optional<Usuario> buscarPorId(Long usuarioId) {
    return springDataUsuarioRepository.findById(usuarioId)
      .map(e -> new Usuario(e.getId(), e.getNome(), e.getCpf(), e.getSaldo()));
  }

}
