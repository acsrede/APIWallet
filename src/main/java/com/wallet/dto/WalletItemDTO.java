package com.wallet.dto;

import java.math.BigDecimal;
import java.util.Date;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;

import lombok.Data;

@Data
public class WalletItemDTO {

  private static final String INVALID_WALLET = "Insira o id da carteira!";
  private static final String INVALID_DATE = "Informe uma data!";
  private static final String INVALID_TYPE = "Informe um tipo!";
  private static final String INVALID_DESCRIPTION = "Informe uma descrição!";
  private static final String INVALID_DESCRIPION_LENGTH = "A descrição deve ter no mínimo 5 catacteres!";
  private static final String INVALID_VALUE = "Informe um valor!";
  private static final String INVALID_ENSD = "Para o tipo somente serão aceitos ENTRADA ou SAÍDA";

  private Long id;
  @NotNull(message = INVALID_WALLET)
  private Long wallet;
  @NotNull(message = INVALID_DATE)
  private Date date;
  @NotNull(message = INVALID_TYPE)
  @Pattern(regexp="^(ENTRADA|SAIDA)$", message = INVALID_ENSD)
  private String type;
  @NotNull(message = INVALID_DESCRIPTION)
  @Length(min = 5, message = INVALID_DESCRIPION_LENGTH)
  private String description;
  @NotNull(message = INVALID_VALUE)
  private BigDecimal value;

}