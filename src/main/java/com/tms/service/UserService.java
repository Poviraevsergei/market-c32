package com.tms.service;

import com.tms.model.User;
import com.tms.repository.SecurityRepository;
import com.tms.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final SecurityService securityService;
    private static final Logger log = LoggerFactory.getLogger(UserService.class);

    @Autowired
    public UserService(UserRepository userRepository, SecurityService securityService) {
        this.userRepository = userRepository;
        this.securityService = securityService;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(Long id) {
       if (securityService.canAccessUser(id)){
         return userRepository.findById(id);
       }
       throw new AccessDeniedException("Access denied login:" + SecurityContextHolder.getContext().getAuthentication().getName() + " by id " + id);
    }

    public Optional<User> updateUser(User user) {
        if(securityService.canAccessUser(user.getId())) {
            return Optional.of(userRepository.save(user));
        }
        throw new AccessDeniedException("Access denied login:" + SecurityContextHolder.getContext().getAuthentication().getName() + " by id " + user.getId());
    }

    public Boolean deleteUser(Long id) {
        if(securityService.canAccessUser(id)) {
            userRepository.deleteById(id);
            return !userRepository.existsById(id);
        }
        throw new AccessDeniedException("Access denied login:" + SecurityContextHolder.getContext().getAuthentication().getName() + " by id " + id);
    }

    public Boolean createUser(User user) {
        user.setDeleted(false);
        try {
            userRepository.save(user);
        } catch (Exception e) {
            log.error(e.getMessage());
            return false;
        }
        return true;
    }
}
