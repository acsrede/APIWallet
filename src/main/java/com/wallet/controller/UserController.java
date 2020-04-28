package com.wallet.controller;

import javax.validation.Valid;

import com.wallet.dto.UserDTO;
import com.wallet.entity.User;
import com.wallet.response.Response;
import com.wallet.service.UserService;
import com.wallet.util.Bcrypt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("user")
public class UserController {

  @Autowired
  private UserService userService;

  @PostMapping
  public ResponseEntity<Response<UserDTO>> Create(@Valid @RequestBody UserDTO dto, BindingResult result) {
    Response<UserDTO> response = new Response<UserDTO>();
    if (result.hasErrors()) {
      result.getAllErrors().forEach(resp -> response.getErrors().add(resp.getDefaultMessage()));
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
    User user = userService.save(this.convertDtoToEntity(dto));
    response.setData(this.convertEntityToDto(user));
    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }

  private User convertDtoToEntity(final UserDTO dto) {
    final User user = new User();
    user.setId(dto.getId());
    user.setName(dto.getName());
    user.setEmail(dto.getEmail());
    user.setPassword(Bcrypt.getHash(dto.getPassword()));
    return user;
  }

  private UserDTO convertEntityToDto(final User user) {
    final UserDTO dto = new UserDTO();
    dto.setId(user.getId());
    dto.setName(user.getName());
    dto.setEmail(user.getEmail());
    return dto;
  }

}
