package com.wallet.dto;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class UserWalletDTO {

  private static final String INVALID_ID_USER = "Informe o ID do usuário!";
  private static final String INVALID_ID_WALLET = "Informe o ID da carteira!";

  private Long id;
  @NotNull(message = INVALID_ID_USER)
  private Long users;
  @NotNull(message = INVALID_ID_WALLET)
  private Long wallet;

}