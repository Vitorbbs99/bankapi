package com.bankapp.bankapi.infrastructure.adapters.outbound;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataUsuarioRepository extends JpaRepository<UsuarioEntity, Long> {
}
