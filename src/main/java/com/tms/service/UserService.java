package com.tms.service;

import com.tms.model.User;
import com.tms.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Optional;

@Service
public class UserService {
    
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<User> getUserById(Long id){
        return userRepository.getUserById(id);
    }

    public Optional<User> updateUser(User user){
        user.setUpdated(new Timestamp(System.currentTimeMillis()));
        return userRepository.updateUser(user);
    }

    public Boolean deleteUser(Long id){
        return userRepository.deleteUser(id);
    }
    
    public Boolean createUser(User user){
        user.setDeleted(false);
        return userRepository.createUser(user);
    }
}
