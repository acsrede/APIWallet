package com.wallet.dto;

import java.math.BigDecimal;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import lombok.Data;

@Data
public class WalletDTO {

  private static final String INVALID_LENGTH = "O nome deve conter no mínimo 3 caracteres!";
  private static final String NOTNULL_NAME = "É obrigatório o preenchimento do campo nome!";
  private static final String NOTNULL_VALUE = "É obrigatório o preenchimento do campo valor!";

  private Long id;
  @Length(min = 3, message = INVALID_LENGTH)
  @NotNull(message = NOTNULL_NAME)
  private String name;
  @NotNull(message = NOTNULL_VALUE)
  private BigDecimal value;

}