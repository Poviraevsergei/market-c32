package com.tms.repository;

import com.tms.config.SQLQuery;
import com.tms.model.Security;
import com.tms.model.User;
import jakarta.persistence.Query;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.Optional;

@Repository
public class SecurityRepository {

    private final Session session;
    private static final Logger log = LoggerFactory.getLogger(SecurityRepository.class);

    @Autowired
    public SecurityRepository(Session session) {
        this.session = session;
    }

    public Optional<User> registration(User user, Security security) throws SQLException {
        try {
            session.getTransaction().begin();
            session.persist(user);
            session.persist(security);
            session.getTransaction().commit();
            return Optional.of(user);
        } catch (Exception e) {
            session.getTransaction().rollback();
            log.error(e.getMessage());
            return Optional.empty();
        }
    }

    public Boolean isLoginUsed(String login) throws SQLException {
        Query query = session.createNativeQuery(SQLQuery.GET_SECURITY_BY_LOGIN, Security.class);
        query.setParameter("login", login);
        return query.getSingleResultOrNull() != null;
    }

    public Optional<Security> getSecurityById(Long id) {
        return Optional.of(session.find(Security.class, id));
    }
}
