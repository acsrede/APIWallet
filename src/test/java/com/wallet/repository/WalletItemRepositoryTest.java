package com.wallet.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.math.BigDecimal;
import java.util.Date;

import com.wallet.entity.Wallet;
import com.wallet.entity.WalletItem;
import com.wallet.util.TypeEnum;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class WalletItemRepositoryTest {

  private static final Date DATE = new Date();
  private static final TypeEnum TYPE = TypeEnum.EN;
  private static final String DESCRIPTION = "Conta de Luz";
  private static final BigDecimal VALUE = BigDecimal.valueOf(65);
  private Long savedWalletItemId = null;
  private Long savedWalletId = null;

  @Autowired
  private WalletItemRepository walletItemRepository;

  @Autowired
  private WalletRepository walletRepository;

  @BeforeEach
  public void setUp() {
    Wallet wallet = new Wallet();
    wallet.setName("Carteira Teste");
    wallet.setValue(BigDecimal.valueOf(250));
    walletRepository.save(wallet);

    WalletItem walletItem = new WalletItem(null, wallet, DATE, TYPE, DESCRIPTION, VALUE);
    walletItemRepository.save(walletItem);
    savedWalletItemId = walletItem.getId();
    savedWalletId = wallet.getId();
  }

  @AfterEach
  public void tearDown() {
    walletItemRepository.deleteAll();
    walletRepository.deleteAll();
  }

  @Test
  public void testSavw() {

    Wallet wallet = new Wallet();
    wallet.setName("Carteira 1");
    wallet.setValue(BigDecimal.valueOf(500));
    walletRepository.save(wallet);

    WalletItem walletItem = new WalletItem(1L, wallet, DATE, TYPE, DESCRIPTION, VALUE);
    WalletItem response = walletItemRepository.save(walletItem);
    assertNotNull(response);
    assertEquals(response.getDescription(), DESCRIPTION);
    assertEquals(response.getType(), TYPE);
    assertEquals(response.getValue(), VALUE);
    assertEquals(response.getWallet().getId(), wallet.getId());
    
  }

}