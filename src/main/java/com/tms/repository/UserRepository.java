package com.tms.repository;

import com.tms.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.MutationQuery;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.tms.config.SQLQuery.COPY_USER_HQL;
import static com.tms.config.SQLQuery.DELETE_BY_ID_HQL;
import static com.tms.config.SQLQuery.FIND_ALL_HQL;
import static com.tms.config.SQLQuery.FIND_BY_ID_HQL;
import static com.tms.config.SQLQuery.UPDATE_USER_BY_ID_HQL;

@Repository
public class UserRepository {

    public SessionFactory sessionFactory;
    private static final Logger log = LoggerFactory.getLogger(UserRepository.class);

    @Autowired
    public UserRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public Optional<User> getUserById(Long id) {
        User user = null;
        try (Session session = sessionFactory.openSession()) {
            Query<User> query = session.createQuery(FIND_BY_ID_HQL, User.class);
            query.setParameter("userId", id);
            user = query.getSingleResult();
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return Optional.ofNullable(user);
    }

    public List<User> getAllUsers() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery(FIND_ALL_HQL, User.class).getResultList();
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return new ArrayList<>();
    }

    public Boolean deleteUser(Long id) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            MutationQuery query = session.createMutationQuery(DELETE_BY_ID_HQL);
            query.setParameter("id", id);
            transaction = session.beginTransaction();
            query.executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            log.error(e.getMessage());
            return false;
        }
        return true;
    }

    public Optional<User> updateUser(User user) {
        Transaction transaction = null;
        User userUpdated = null;
        try (Session session = sessionFactory.openSession()) {
            MutationQuery query = session.createMutationQuery(UPDATE_USER_BY_ID_HQL);
            query.setParameter("firstname", user.getFirstname());
            query.setParameter("secondName", user.getSecondName());
            query.setParameter("age", user.getAge());
            query.setParameter("email", user.getEmail());
            query.setParameter("sex", user.getSex());
            query.setParameter("telephoneNumber", user.getTelephoneNumber());
            query.setParameter("updated", Timestamp.valueOf(LocalDateTime.now()));
            query.setParameter("isDeleted", user.getDeleted());
            query.setParameter("id", user.getId());

            transaction = session.beginTransaction();
            query.executeUpdate();
            transaction.commit();

            Query<User> queryFindUser = session.createQuery(FIND_BY_ID_HQL, User.class);
            queryFindUser.setParameter("userId", user.getId());
            userUpdated = queryFindUser.getSingleResult();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            log.error(e.getMessage());
        }
        return Optional.ofNullable(userUpdated);
    }

    public Boolean createUser(User user) {
        /* Не предназначен для добавления строк в БД(кроме копирования из 1 таблицы в 2*) */

        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            Query<User> query = session.createQuery(COPY_USER_HQL, User.class);
            query.setParameter("id", user.getId());
            query.executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            log.error(e.getMessage());
            return false;
        }
        return true;
    }
}