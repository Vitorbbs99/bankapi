package com.bankapp.bankapi.infrastructure.controller;

import com.bankapp.bankapi.domain.entity.Usuario;
import com.bankapp.bankapi.domain.model.Paginacao;
import com.bankapp.bankapi.domain.ports.inbound.UsuarioUseCase;
import com.bankapp.bankapi.infrastructure.adapters.inbound.dto.DepositoRequestDTO;
import com.bankapp.bankapi.infrastructure.adapters.inbound.dto.UsuarioRequestDTO;
import com.bankapp.bankapi.infrastructure.adapters.inbound.dto.UsuarioResponseDTO;
import com.bankapp.bankapi.infrastructure.adapters.outbound.UsuarioEntity;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/usuarios")
public class UsuarioController {

  private final UsuarioUseCase usuarioUseCase;

  public UsuarioController(UsuarioUseCase usuarioUseCase) {
    this.usuarioUseCase = usuarioUseCase;
  }

  @PostMapping
  public ResponseEntity<UsuarioResponseDTO> criarUsuario (@RequestBody UsuarioRequestDTO usuarioRequestDTO) {
    Usuario usuario = new Usuario(
      null,
      usuarioRequestDTO.nome(),
      usuarioRequestDTO.cpf(),
      usuarioRequestDTO.saldo()
    );

    Usuario criado = usuarioUseCase.criarUsuario(usuario);
    return ResponseEntity.status(HttpStatus.CREATED).body(UsuarioResponseDTO.doDominio(criado));
  }

  @PutMapping("/{id}")
  public ResponseEntity<UsuarioResponseDTO> editarUsuario(
    @PathVariable("id") Long usuarioId,
    @RequestBody @Valid UsuarioRequestDTO usuarioRequestDTO
  ) {

    Usuario usuarioParaEditar = usuarioRequestDTO.paraDominio();

    Usuario usuarioAtualizado = usuarioUseCase.editarUsuario(usuarioId, usuarioParaEditar);

    return ResponseEntity.ok(UsuarioResponseDTO.doDominio(usuarioAtualizado));
  }

  @GetMapping
  public ResponseEntity<Paginacao<UsuarioResponseDTO>> listarUsuarios(
    @RequestParam(defaultValue = "0") int page,
    @RequestParam(defaultValue = "20") int size
  ) {
    Paginacao<Usuario> paginaUsuarios = usuarioUseCase.listarUsuarios(page, size);

    List<UsuarioResponseDTO> responseDTOS = paginaUsuarios.conteudo().stream()
      .map(UsuarioResponseDTO::doDominio)
      .toList();

    return ResponseEntity.ok(new Paginacao<>(responseDTOS, paginaUsuarios.pagina(),
      paginaUsuarios.tamanho(), paginaUsuarios.totalElementos(), paginaUsuarios.totalPaginas()));
  }

  @GetMapping("/{id}")
  public ResponseEntity<UsuarioResponseDTO> listaUsuario(@PathVariable("id") Long usuarioId) {
    Usuario usuario = usuarioUseCase.listaUsuario(usuarioId);
    return ResponseEntity.ok(UsuarioResponseDTO.doDominio(usuario));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> removerUsuario(@PathVariable Long id) {
    usuarioUseCase.removerUsuario(id);
    return ResponseEntity.noContent().build();
  }

  @PostMapping("/{id}/deposito")
  public ResponseEntity<UsuarioResponseDTO> depositar (
    @PathVariable Long id,
    @RequestBody DepositoRequestDTO dto
    ) {
      Usuario usuario = usuarioUseCase.realizarDeposito(id, dto.valor());
      return ResponseEntity.ok(UsuarioResponseDTO.doDominio(usuario));
  }

  @PostMapping("/{id}/saque")
  public ResponseEntity<UsuarioResponseDTO> sacar (
    @PathVariable Long id,
    @RequestBody DepositoRequestDTO dto
  ) {
    Usuario usuario = usuarioUseCase.realizarSaque(id, dto.valor());
    return ResponseEntity.ok(UsuarioResponseDTO.doDominio(usuario));
  }
}
