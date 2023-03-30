package com.bank.registerloginservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.bank.registerloginservice.dto.LoginUserRequest;
import com.bank.registerloginservice.dto.RegisterRequestDto;
import com.bank.registerloginservice.dto.UserDetails;
import com.bank.registerloginservice.exceptions.InvalidTokenException;
import com.bank.registerloginservice.exceptions.UserNotFoundException;
import com.bank.registerloginservice.service.IUserService;

@RestController
public class AppController {
	@Autowired
	private IUserService service;
    @PostMapping("/register")
    public UserDetails register(@RequestBody RegisterRequestDto requestData) {
        System.out.println("**inside register");
        UserDetails response = service.register(requestData);
        return response;
    }

    @PostMapping("/login")
    public String login(@RequestBody LoginUserRequest request) throws Exception {
        System.out.println("**inside login");
        String token = service.login(request);
        return token;
    }
    @GetMapping("/checkByToken/{token}")
    public UserDetails checkToken(@PathVariable String token) throws InvalidTokenException, UserNotFoundException
    {
    	return service.authenticateByToken(token);
    }
}

