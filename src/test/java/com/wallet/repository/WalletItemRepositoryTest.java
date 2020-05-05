package com.wallet.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Optional;

import javax.validation.ConstraintViolationException;

import com.wallet.entity.Wallet;
import com.wallet.entity.WalletItem;
import com.wallet.util.TypeEnum;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
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

  @Before
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

  @After
  public void tearDown() {
    walletItemRepository.deleteAll();
    walletRepository.deleteAll();
  }

  @Test
  public void testSave() {

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

  @Test(expected = ConstraintViolationException.class)
  public void TestSaveInvalidWalletItem() {
    WalletItem walletItem = new WalletItem(null, null, DATE, null, DESCRIPTION, null);
    walletItemRepository.save(walletItem);
  }

  @Test
  public void testUpdate() {
    Optional<WalletItem> walletItem = walletItemRepository.findById(savedWalletItemId);
    String description = "Descrição alterada";
    WalletItem changed = walletItem.get();
    changed.setDescription(description);
    walletItemRepository.save(changed);
    Optional<WalletItem> newWalletItem = walletItemRepository.findById(savedWalletItemId);
    assertEquals(description, newWalletItem.get().getDescription());
  }

  @Test
  public void deleteWalletItem() {
    Optional<Wallet> wallet = walletRepository.findById(savedWalletId);
    WalletItem walletItem = new WalletItem(null, wallet.get(), DATE, TYPE, DESCRIPTION, VALUE);
    walletItemRepository.save(walletItem);
    walletItemRepository.deleteById(walletItem.getId());
    Optional<WalletItem> response = walletItemRepository.findById(walletItem.getId());
    assertFalse(response.isPresent());
  }
}