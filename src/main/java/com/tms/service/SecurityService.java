package com.tms.service;

import com.tms.exception.LoginUsedException;
import com.tms.model.Role;
import com.tms.model.Security;
import com.tms.model.User;
import com.tms.model.dto.RegistrationRequestDto;
import com.tms.repository.SecurityRepository;
import com.tms.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Component
public class SecurityService {

    private final UserRepository userRepository;
    private User user;
    private Security security;
    private final SecurityRepository securityRepository;

    @Autowired
    public SecurityService(SecurityRepository securityRepository, User user, Security security, UserRepository userRepository) {
        this.securityRepository = securityRepository;
        this.user = user;
        this.security = security;
        this.userRepository = userRepository;
    }

    public Optional<Security> getSecurityById(Long id) {
        return securityRepository.findById(id);
    }

    @Transactional(rollbackFor = Exception.class)
    public Optional<User> registration(RegistrationRequestDto requestDto) throws LoginUsedException {
        if (securityRepository.existsByLogin(requestDto.getLogin())) {
            throw new LoginUsedException(requestDto.getLogin());
        }
        
        user.setFirstname(requestDto.getFirstname());
        user.setSecondName(requestDto.getSecondName());
        user.setEmail(requestDto.getEmail());
        user.setAge(requestDto.getAge());
        user.setSex(requestDto.getSex());
        user.setTelephoneNumber(requestDto.getTelephoneNumber());
        user.setDeleted(false);
        User userUpdated = userRepository.save(user);

        security.setLogin(requestDto.getLogin());
        security.setPassword(requestDto.getPassword());
        security.setRole(Role.USER);
        security.setUserId(userUpdated.getId());
        securityRepository.save(security);
        return userRepository.findById(userUpdated.getId());
    }
}
