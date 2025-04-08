package com.tms.repository;

import com.tms.model.User;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaDelete;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.CriteriaUpdate;
import jakarta.persistence.criteria.Root;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
            //Создаем CriteriaBuilder
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();

            //Создаем CriteriaQuery
            CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);

            //Корень запроса
            Root<User> root = criteriaQuery.from(User.class);

            criteriaQuery.select(root).where(criteriaBuilder.equal(root.get("id"), id));

            //Выполняем запрос
            user = session.createQuery(criteriaQuery).getSingleResult();
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return Optional.ofNullable(user);
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try (Session session = sessionFactory.openSession()) {
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);
            Root<User> root = criteriaQuery.from(User.class);

            criteriaQuery.select(root);

            users = session.createQuery(criteriaQuery).getResultList();
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return users;
    }

    public Boolean deleteUser(Long id) {
        Transaction transaction = null;
        boolean isDeleted = false;
        try (Session session = sessionFactory.openSession()) {
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaDelete<User> criteriaQuery = criteriaBuilder.createCriteriaDelete(User.class);
            Root<User> root = criteriaQuery.from(User.class);

            criteriaQuery.where(criteriaBuilder.equal(root.get("id"), id));

            transaction = session.beginTransaction();
            int affectedRows = session.createMutationQuery(criteriaQuery).executeUpdate();
            transaction.commit();
            isDeleted = affectedRows > 0;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            log.error(e.getMessage());
        }
        return isDeleted;
    }

    public Optional<User> updateUser(User user) {
        Transaction transaction = null;
        User userUpdated = null;
        try (Session session = sessionFactory.openSession()) {
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaUpdate<User> criteriaQuery = criteriaBuilder.createCriteriaUpdate(User.class);
            Root<User> root = criteriaQuery.from(User.class);

            criteriaQuery.set(root.get("firstname"), user.getFirstname());
            criteriaQuery.set(root.get("secondName"), user.getSecondName());
            criteriaQuery.set(root.get("age"), user.getAge());
            criteriaQuery.set(root.get("email"), user.getEmail());
            criteriaQuery.set(root.get("sex"), user.getSex());
            criteriaQuery.set(root.get("telephoneNumber"), user.getTelephoneNumber());
            criteriaQuery.set(root.get("updated"), Timestamp.valueOf(LocalDateTime.now()));
            criteriaQuery.set(root.get("isDeleted"), user.getDeleted());

            criteriaQuery.where(criteriaBuilder.equal(root.get("id"), user.getId()));

            transaction = session.beginTransaction();
            int affectedRows = session.createMutationQuery(criteriaQuery).executeUpdate();
            transaction.commit();

            if (affectedRows > 0) {
                userUpdated = user;
            }
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            log.error(e.getMessage());
        }
        return Optional.ofNullable(userUpdated);
    }

    public Boolean createUser(User user) {
        /* Не предназначен для добавления строк в БД*/

        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.getTransaction();
            transaction.begin();
            session.persist(user);
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