package com.tms.repository;

import com.tms.model.User;
import org.hibernate.Session;
import jakarta.persistence.EntityNotFoundException;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class UserRepository {
    
    public Session session;
    public SessionFactory sessionFactory;
    private static final Logger log = LoggerFactory.getLogger(UserRepository.class);

    @Autowired
    public UserRepository(Session session, SessionFactory sessionFactory) {
        this.session = session;
        this.sessionFactory = sessionFactory;
    }

    public Optional<User> getUserById(Long id) {
        Session sessionOne = sessionFactory.openSession();
        Optional<User> userOpt = Optional.of(session.find(User.class, id));
        sessionOne.close();
        return userOpt;
    }
    
    public Boolean deleteUser(Long id) {
        try {
            session.getTransaction().begin();
            session.remove(session.find(User.class, id));
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            log.error(e.getMessage());
            return false;
        }
        return true;
    }

    public Boolean createUser(User user) {
        try {
            session.getTransaction().begin();
            session.persist(user); 
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            log.error(e.getMessage());
            return false;
        }
        return true;
    }
    
    public List<User> getAllUsers(){
        return session.createQuery("from users").getResultList();
    }

    public Optional<User> updateUser(User user) {
        User existingUser = session.find(User.class, user.getId());
        
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
            session.getTransaction().begin();
            session.merge(existingUser);
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            log.error(e.getMessage());
        }
        return Optional.of(existingUser);
    }
}