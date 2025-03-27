package com.tms.controller;

import com.tms.exception.LoginUsedException;
import com.tms.model.User;
import com.tms.model.dto.RegistrationRequestDto;
import com.tms.service.SecurityService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/security")
public class SecurityController {

    public SecurityService securityService;

    @Autowired
    public SecurityController(SecurityService securityService) {
        this.securityService = securityService;
    }

    @Operation(summary = "User registration", description = "Endpoint allows to register a new user. Checks validation. In the database creates 2 new models related to each other (User, Security)")
    @PostMapping("/registration")
    public ResponseEntity<User> registration(@RequestBody @Valid RegistrationRequestDto requestDto,
                                             BindingResult bindingResult) throws LoginUsedException {
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Optional<User> user = securityService.registration(requestDto);
        if (user.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(user.get(), HttpStatus.CREATED);
    }
}