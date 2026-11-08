package com.bankapp.bankapi.infrastructure.adapters.outbound;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

@Document(collection = "usuario_bank")
public class UsuarioEntity {

  @Id
  private String id;
  private String nome;
  private String cpf;
  private BigDecimal saldo;

  public UsuarioEntity() {}

  public UsuarioEntity(String id, String nome, String cpf, BigDecimal saldo) {
    this.id = id;
    this.nome = nome;
    this.cpf = cpf;
    this.saldo = saldo;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getNome() {
    return nome;
  }

  public void setNome(String nome) {
    this.nome = nome;
  }

  public String getCpf() {
    return cpf;
  }

  public void setCpf(String cpf) {
    this.cpf = cpf;
  }

  public BigDecimal getSaldo() {
    return saldo;
  }

  public void setSaldo(BigDecimal saldo) {
    this.saldo = saldo;
  }
}
