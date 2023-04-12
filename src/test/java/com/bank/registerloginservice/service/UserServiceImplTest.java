package com.bank.registerloginservice.service;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import com.bank.registerloginservice.dto.LoginUserRequest;
import com.bank.registerloginservice.dto.RegisterRequestDto;
import com.bank.registerloginservice.dto.UserDetails;
import com.bank.registerloginservice.entity.AppUser;
import com.bank.registerloginservice.exceptions.IncorrectCredentialsException;
import com.bank.registerloginservice.exceptions.InvalidTokenException;
import com.bank.registerloginservice.exceptions.UserNotFoundException;
import com.bank.registerloginservice.repo.IUserRepository;
import com.bank.registerloginservice.util.TokenUtil;
import com.bank.registerloginservice.util.UserUtil;
@ExtendWith(MockitoExtension.class)
@SpringBootTest
class UserServiceImplTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private IUserRepository userRepository;

    @Mock
    private UserUtil userUtil;

    @Mock
    private TokenUtil tokenUtil;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testFindByUsernameSuccess() throws Exception {
        String username = "john.doe";
        AppUser expectedResponse = new AppUser();
        expectedResponse.setId(1L);
        expectedResponse.setUsername(username);

        when(userRepository.findByUsername(username)).thenReturn(Optional.of(expectedResponse));

        AppUser actualResponse = userService.findByUsername(username);

        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    public void testFindByUsernameFailure() throws Exception {
        String username = "jane.doe";

        when(userRepository.findByUsername(username)).thenReturn(Optional.empty());

        Exception exception = assertThrows(UserNotFoundException.class, () -> {
            userService.findByUsername(username);
        });

        assertEquals("user not found for username=" + username, exception.getMessage());
    }

    @Test
    public void testFindUserDetailsByUsername() throws Exception {
        String username = "john.doe";
        AppUser user = new AppUser();
        user.setId(1L);
        user.setUsername(username);

        UserDetails expectedResponse = new UserDetails();
        expectedResponse.setId(1);
        expectedResponse.setUsername(username);

        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));
        when(userUtil.toUserDetails(user)).thenReturn(expectedResponse);

        UserDetails actualResponse = userService.findUserDetailsByUsername(username);

        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    public void testRegister() throws Exception {
        String username = "john.doe";
        String password = "password";
        RegisterRequestDto request = new RegisterRequestDto(username, password);

        AppUser user = new AppUser(username, password);
        when(userRepository.save(any())).thenReturn(user);
        when(userUtil.toUserDetails(user)).thenReturn(new UserDetails(username,"password"));

        UserDetails userDetails = userService.register(request);
        assertNotNull(userDetails);
        assertEquals(username, userDetails.getUsername());

        verify(userRepository).save(any());
        verify(userUtil).toUserDetails(user);
    }
}