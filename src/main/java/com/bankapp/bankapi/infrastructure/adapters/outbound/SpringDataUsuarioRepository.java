package com.bankapp.bankapi.infrastructure.adapters.outbound;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface SpringDataUsuarioRepository extends MongoRepository<UsuarioEntity, String> {
}
