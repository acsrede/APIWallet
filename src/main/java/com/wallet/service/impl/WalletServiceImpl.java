package com.wallet.service.impl;

import com.wallet.entity.Wallet;
import com.wallet.repository.WalletRepository;
import com.wallet.service.WalletService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WalletServiceImpl implements WalletService {

  @Autowired
  private WalletRepository walletRepository;

  @Override
  public Wallet save(Wallet wallet) {
    return walletRepository.save(wallet);
  }

}