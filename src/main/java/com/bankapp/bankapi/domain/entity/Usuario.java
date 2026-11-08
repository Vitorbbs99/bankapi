package com.bankapp.bankapi.domain.entity;

import java.math.BigDecimal;

public class Usuario {
  private String id;
  private String nome;
  private String cpf;
  private BigDecimal saldo;

  public Usuario() {
  }

  public Usuario(String id, String nome, String cpf, BigDecimal saldo) {
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
