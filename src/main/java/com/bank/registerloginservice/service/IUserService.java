package com.bank.registerloginservice.service;


import org.hibernate.validator.constraints.Length;
import org.springframework.validation.annotation.Validated;

import com.bank.registerloginservice.dto.LoginUserRequest;
import com.bank.registerloginservice.dto.RegisterRequestDto;
import com.bank.registerloginservice.dto.UserDetails;
import com.bank.registerloginservice.entity.AppUser;
import com.bank.registerloginservice.exceptions.IncorrectCredentialsException;
import com.bank.registerloginservice.exceptions.InvalidTokenException;
import com.bank.registerloginservice.exceptions.UserNotFoundException;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

@Validated
public interface IUserService {

    AppUser findByUsername(@NotBlank @Length(min = 2, max = 15) String username) throws UserNotFoundException;

    UserDetails findUserDetailsByUsername(@NotBlank @Length(min = 2, max = 15) String username) throws UserNotFoundException;

    UserDetails register(@Valid RegisterRequestDto request);

    UserDetails authenticateByToken(String token)throws InvalidTokenException,UserNotFoundException ;

    String login(LoginUserRequest request) throws IncorrectCredentialsException;

}
