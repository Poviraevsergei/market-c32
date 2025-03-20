package com.tms;

import com.tms.controller.UserController;
import com.tms.model.User;
import com.tms.service.UserService;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyLong;

public class MainTest {

    @Mock
    UserService userService;

    @InjectMocks
    UserController userController;

    private User user;

    @BeforeAll
    public static void beforeAll() {
        System.out.println("Before All");
    }

    @AfterAll
    public static void afterAll() {
        System.out.println("After All");
    }

    @BeforeEach
    public void beforeEach() {
        MockitoAnnotations.initMocks(this);
        System.out.println("Before Each");
        user = new User();
        user.setId(3L);
        user.setFirstname("Dimas");
    }

    @AfterEach
    public void afterEach() {
        System.out.println("After Each");
    }

    @Test
    public void testGetUserById_Success() {
        //1. Настройка перед запуском
        Mockito.when(userService.getUserById(anyLong())).thenReturn(Optional.of(user));

        //2. Запуск метода
        ResponseEntity<User> response = userController.getUserById(3L);

        //3. Сравнение результатов
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals(user, response.getBody());
    }

    @Test
    public void testGetUserById_NotFound() {
        //1. Настройка перед запуском
        Mockito.when(userService.getUserById(anyLong())).thenReturn(Optional.empty());

        //2. Запуск метода
        ResponseEntity<User> response = userController.getUserById(3L);

        //3. Сравнение результатов
        Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        Assertions.assertNull(response.getBody());
    }
}
/*
        Assertions.fail();
        Assertions.assertEquals(4,2+2);
        Assertions.assertNotEquals(5,2+2);
        Assertions.assertNull(null);
        Assertions.assertNotNull(user1);
        Assertions.assertTrue(true);
        Assertions.assertFalse(false);
        Assertions.assertArrayEquals(new int[]{1,2,3},new int[]{1,2,3});
        Assertions.assertThrows(IllegalArgumentException.class, () -> {throw new IllegalArgumentException("Illegal exception");});
        Assertions.assertTimeout(Duration.ofMillis(50), () -> {Thread.sleep(1);});
 */