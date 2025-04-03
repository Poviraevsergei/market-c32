package com.tms.repository;

import com.tms.config.SQLQuery;
import com.tms.model.Security;
import com.tms.model.User;
import com.tms.model.dto.RegistrationRequestDto;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.Optional;

@Repository
public class SecurityRepository {

    private final EntityManager entityManager;
    private static final Logger log = LoggerFactory.getLogger(SecurityRepository.class);

    @Autowired
    public SecurityRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public Optional<User> registration(User user, Security security) throws SQLException {
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(user);
            entityManager.persist(security);
            entityManager.getTransaction().commit();
            return Optional.of(user);
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            log.error(e.getMessage());
            return Optional.empty();
        }
    }

    public Boolean isLoginUsed(String login) throws SQLException {
        Query query = entityManager.createNativeQuery(SQLQuery.GET_SECURITY_BY_LOGIN, Security.class);
        query.setParameter("login", login);
        return query.getSingleResultOrNull() != null;
    }

    public Optional<Security> getSecurityById(Long id) {
        return Optional.of(entityManager.find(Security.class, id));
    }
}
