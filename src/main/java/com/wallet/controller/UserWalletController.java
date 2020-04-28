package com.wallet.controller;

import javax.validation.Valid;

import com.wallet.dto.UserWalletDTO;
import com.wallet.entity.User;
import com.wallet.entity.UserWallet;
import com.wallet.entity.Wallet;
import com.wallet.response.Response;
import com.wallet.service.UserWalletService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("user-wallet")
public class UserWalletController {

  @Autowired
  UserWalletService userWalletService;

  @PostMapping
  public ResponseEntity<Response<UserWalletDTO>> create(@Valid @RequestBody UserWalletDTO dto, BindingResult result) {
    Response<UserWalletDTO> response = new Response<UserWalletDTO>();
    if (result.hasErrors()) {
      result.getAllErrors().forEach(resp -> response.getErrors().add(resp.getDefaultMessage()));
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
    UserWallet userWallet = userWalletService.save(this.convertDtoToEntity(dto));
    response.setData(this.convertEntityToDto(userWallet));
    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }

  private UserWallet convertDtoToEntity(UserWalletDTO dto) {
    UserWallet userWallet = new UserWallet();
    User user = new User();
    Wallet wallet = new Wallet();
    user.setId(dto.getUsers());
    wallet.setId(dto.getWallet());
    userWallet.setId(dto.getId());
    userWallet.setUsers(user);
    userWallet.setWallet(wallet);
    return userWallet;
  }

  private UserWalletDTO convertEntityToDto(UserWallet userWallet) {
    UserWalletDTO userWalletDTO = new UserWalletDTO();
    userWalletDTO.setId(userWallet.getId());
    userWalletDTO.setUsers(userWallet.getUsers().getId());
    userWalletDTO.setWallet(userWallet.getWallet().getId());
    return userWalletDTO;
  }

}