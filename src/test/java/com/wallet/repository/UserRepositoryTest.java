package com.wallet.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import com.wallet.entity.User;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
public class UserRepositoryTest {

  private static final String EMAIL = "email@teste.com";

  @Autowired
  UserRepository userRepository;

  @BeforeEach
  public void setUp() {
    User user = new User();
    user.setName("Set up user");
    user.setPassword("senha123");
    user.setEmail(EMAIL);

    userRepository.save(user);
  }

  @AfterEach
  public void tearDown() {
    userRepository.deleteAll();
  }

  @Test
  public void testSave() {
    User user = new User();
    user.setName("Teste");
    user.setPassword("123456");
    user.setEmail("teste@teste.com");

    User response = userRepository.save(user);
    assertNotNull(response);
  }

  @Test
  public void testFindByEmail() {
    Optional<User> response = userRepository.findByEmailEquals(EMAIL);

    assertTrue(response.isPresent());
    assertEquals(response.get().getEmail(), EMAIL);
  }

}