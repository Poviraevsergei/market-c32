package com.tms.repository;


import com.tms.model.User;
import jakarta.persistence.EntityManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class UserRepository {
    
    public EntityManager entityManager;
    private static final Logger log = LoggerFactory.getLogger(UserRepository.class);

    @Autowired
    public UserRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public Optional<User> getUserById(Long id) {
        return Optional.of(entityManager.find(User.class, id));
    }

    public Boolean deleteUser(Long id) {
        try {
            entityManager.getTransaction().begin();
            entityManager.remove(entityManager.find(User.class, id));
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            log.error(e.getMessage());
            return false;
        }
        return true;
    }

    public Boolean createUser(User user) {
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(user); 
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            log.error(e.getMessage());
            return false;
        }
        return true;
    }
    
    public List<User> getAllUsers(){
        return entityManager.createQuery("from users").getResultList();
    }

    public Optional<User> updateUser(User user) {
        User userUpdated = null;
        
        try {
            entityManager.getTransaction().begin();
            userUpdated = entityManager.merge(user);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            log.error(e.getMessage());
        }
        return Optional.of(userUpdated);
    }
}