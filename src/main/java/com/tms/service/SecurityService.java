package com.tms.service;

import com.tms.exception.LoginUsedException;
import com.tms.model.Role;
import com.tms.model.Security;
import com.tms.model.User;
import com.tms.model.dto.RegistrationRequestDto;
import com.tms.repository.SecurityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Optional;

@Component
public class SecurityService {

    private User user;
    private Security security;
    private final SecurityRepository securityRepository;

    @Autowired
    public SecurityService(SecurityRepository securityRepository, User user, Security security) {
        this.securityRepository = securityRepository;
        this.user = user;
        this.security = security;
    }

    public Optional<Security> getSecurityById(Long id) {
        return securityRepository.findById(id);
    }

    public Optional<User> registration(RegistrationRequestDto requestDto) throws LoginUsedException {

        if (securityRepository.existsByLogin(requestDto.getLogin())) {
            throw new LoginUsedException(requestDto.getLogin());
        }
        /*        try {
            
            user.setFirstname(requestDto.getFirstname());
            user.setSecondName(requestDto.getSecondName());
            user.setEmail(requestDto.getEmail());
            user.setUpdated(new Timestamp(System.currentTimeMillis()));
            user.setAge(requestDto.getAge());
            user.setSex(requestDto.getSex());
            user.setTelephoneNumber(requestDto.getTelephoneNumber());
            user.setDeleted(false);
            user.setSecurity(security);

            security.setLogin(requestDto.getLogin());
            security.setPassword(requestDto.getPassword());
            security.setRole(Role.USER);
            security.setUser(user);
            return securityRepository.registration(user, security);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return Optional.empty();
        }*/
        return Optional.ofNullable(null);
    }
}
