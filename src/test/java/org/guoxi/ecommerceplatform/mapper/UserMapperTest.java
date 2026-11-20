package org.guoxi.ecommerceplatform.mapper;

import org.guoxi.ecommerceplatform.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class UserMapperTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    void insert_ShouldSaveUserSuccessfully() {
        String username = "001";
        String phone = String.format("138%08d", ThreadLocalRandom.current().nextInt(100_000_000));
        User user = User.builder()
                .username(username)
                .password("123456")
                .phone(phone)
                .status(1)
                .build();

        int rows = userMapper.insert(user);

        assertEquals(1, rows);
        assertNotNull(user.getId());
        User savedUser = userMapper.selectById(user.getId());
        assertNotNull(savedUser);
        assertEquals(username, savedUser.getUsername());
        assertEquals("123456", savedUser.getPassword());
        assertEquals(phone, savedUser.getPhone());
        assertEquals(1, savedUser.getStatus());
    }
}
