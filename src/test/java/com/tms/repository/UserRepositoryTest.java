package com.tms.repository;

import com.tms.config.SQLQuery;
import com.tms.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class UserRepositoryTest {
    
   
    @Test
    public void testGetUserById_NotFound() throws SQLException {
       //todo: write tests
    }
}
