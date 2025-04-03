package com.tms.repository;


import com.tms.model.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityNotFoundException;
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
        entityManager.clear();
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
        User existingUser = entityManager.find(User.class, user.getId());
        
        if (existingUser == null) {
            throw new EntityNotFoundException("User with id " + user.getId() + " not found");
        }

        existingUser.setFirstname(user.getFirstname());
        existingUser.setSecondName(user.getSecondName());
        existingUser.setAge(user.getAge());
        existingUser.setSex(user.getSex());
        existingUser.setTelephoneNumber(user.getTelephoneNumber());
        existingUser.setDeleted(user.getDeleted());
        existingUser.setEmail(user.getEmail());
        
        try {
            entityManager.getTransaction().begin();
            entityManager.merge(existingUser);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            log.error(e.getMessage());
        }
        return Optional.of(existingUser);
    }
}