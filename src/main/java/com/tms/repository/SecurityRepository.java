package com.tms.repository;

import com.tms.model.Security;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SecurityRepository extends JpaRepository<Security, Long> {
    Boolean existsByLogin(String login);
    
    Optional<Security> findByLogin(String login);
}
