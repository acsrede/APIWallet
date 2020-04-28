package com.wallet.controller;

import javax.validation.Valid;

import com.wallet.dto.WalletDTO;
import com.wallet.entity.Wallet;
import com.wallet.response.Response;
import com.wallet.service.WalletService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("wallet")
public class WalletController {

  @Autowired
  private WalletService walletService;

  @PostMapping
  public ResponseEntity<Response<WalletDTO>> create(@Valid @RequestBody WalletDTO dto, BindingResult result) {    
    Response<WalletDTO> response = new Response<WalletDTO>();
    if (result.hasErrors()) {
      result.getAllErrors().forEach(resp -> response.getErrors().add(resp.getDefaultMessage()));
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
    Wallet wallet = walletService.save(this.convertDtoToEntity(dto));
    response.setData(this.convertEntityToDto(wallet));
    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }

  private Wallet convertDtoToEntity(WalletDTO dto) {
    Wallet wallet = new Wallet();
    wallet.setId(dto.getId());
    wallet.setName(dto.getName());
    wallet.setValue(dto.getValue());
    return wallet;
  }

  private WalletDTO convertEntityToDto(Wallet wallet) {
    WalletDTO walletDTO = new WalletDTO();
    walletDTO.setId(wallet.getId());
    walletDTO.setName(wallet.getName());
    walletDTO .setValue(wallet.getValue());
    return walletDTO;
  }

}