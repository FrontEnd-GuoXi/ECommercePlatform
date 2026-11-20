package org.guoxi.ecommerceplatform.service.impl;

import org.guoxi.ecommerceplatform.dto.LoginDTO;
import org.guoxi.ecommerceplatform.entity.User;
import org.guoxi.ecommerceplatform.mapper.UserMapper;
import org.guoxi.ecommerceplatform.util.JwtUtils;
import org.guoxi.ecommerceplatform.vo.LoginVO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserMapper userMapper;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    @Mock
    private JwtUtils jwtUtils;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void login_WithValidCredentials_ShouldReturnLoginVO() {
        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setAccount("testuser");
        loginDTO.setPassword("123456");

        User user = User.builder()
                .id(1L)
                .username("testuser")
                .password("encodedPassword")
                .phone("13800138000")
                .status(1)
                .build();

        when(userMapper.selectByAccount("testuser")).thenReturn(user);
        when(passwordEncoder.matches("123456", "encodedPassword")).thenReturn(true);
        when(jwtUtils.generateToken(1L, "testuser")).thenReturn("mocked-jwt-token");

        LoginVO result = userService.login(loginDTO);

        assertNotNull(result);
        assertEquals("mocked-jwt-token", result.getToken());
        assertEquals(1L, result.getUserId());
        assertEquals("testuser", result.getUsername());
        assertEquals("13800138000", result.getPhone());
        verify(userMapper).selectByAccount("testuser");
        verify(passwordEncoder).matches("123456", "encodedPassword");
        verify(jwtUtils).generateToken(1L, "testuser");
    }
}
