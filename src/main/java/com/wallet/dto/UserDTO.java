package com.wallet.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;

import org.hibernate.validator.constraints.Length;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDTO {

  private static final String INVALID_EMAIL = "Email inválido!";
  private static final String INVALID_NAME = "O nome deve conter entre 3 e 50 caracteres!";
  private static final String INVALID_PASSWORD = "A senha deve conter no mínimo 6 caracteres!";

  private Long id;
  @Length(min=3, max=50, message=INVALID_NAME)
  private String name;
  @Email(message=INVALID_EMAIL)
  private String email;
  @NotNull
  @Length(min=6, message=INVALID_PASSWORD)
  private String password;

}