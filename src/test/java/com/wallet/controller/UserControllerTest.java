package com.wallet.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wallet.dto.UserDTO;
import com.wallet.entity.User;
import com.wallet.service.UserService;

import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class UserControllerTest {

  private static final Long ID = 1L;
  private static final String NAME = "User Test";
  private static final String EMAIL = "email@test.com";
  private static final String PASSWORD = "123456";
  private static final String URL = "/user";

  @MockBean
  UserService userService;

  @Autowired
  MockMvc mvc;

  @Test
  public void testSave() throws Exception {
    BDDMockito.given(userService.save(Mockito.any(User.class))).willReturn(getMockUser());
    mvc.perform(MockMvcRequestBuilders.post(URL).content(getJsonPayload(ID, NAME, EMAIL, PASSWORD))
      .contentType(MediaType.APPLICATION_JSON)
      .accept(MediaType.APPLICATION_JSON))
    .andExpect(status().isCreated())
    .andExpect(jsonPath("$.data.id").value(ID))
    .andExpect(jsonPath("$.data.name").value(NAME))
    .andExpect(jsonPath("$.data.email").value(EMAIL))
    .andExpect(jsonPath("$.data.password").doesNotExist());
  }

  @Test
  public void testSaveInvalidUser() throws Exception {
    mvc.perform(MockMvcRequestBuilders.post(URL).content(getJsonPayload(ID, NAME, "email", PASSWORD))
      .contentType(MediaType.APPLICATION_JSON)
      .accept(MediaType.APPLICATION_JSON))
    .andExpect(status().isBadRequest())
    .andExpect(jsonPath("$.errors[0]").value("Email inválido!"));
  }

  public User getMockUser() {
    User user = new User();
    user.setId(ID);
    user.setName(NAME);
    user.setEmail(EMAIL);
    user.setPassword(PASSWORD);
    return user;
  }

  public String getJsonPayload(Long id, String name, String email, String password) throws JsonProcessingException {
    UserDTO dto = new UserDTO();
    dto.setId(id);
    dto.setName(name);
    dto.setEmail(email);
    dto.setPassword(password);

    final ObjectMapper mapper = new ObjectMapper();
    return mapper.writeValueAsString(dto);
  }

}