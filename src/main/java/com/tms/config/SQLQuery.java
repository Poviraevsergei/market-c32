package com.tms.config;

public interface SQLQuery {
    String GET_USER_BY_ID = "SELECT * FROM users WHERE id = ?";
    String GET_SECURITY_BY_LOGIN = "SELECT * FROM security WHERE login = :login";
    String DELETE_USER = "UPDATE users SET is_deleted=true, updated=DEFAULT WHERE id = ?";
    String UPDATE_USER = "UPDATE users SET firstname=?,second_name=?,age=?,telephone_number=?,email=?,sex=?,updated=DEFAULT WHERE id=?";
    String CREATE_USER = "INSERT INTO users (id, firstname, second_name, age, telephone_number, email, created, updated, sex, is_deleted) " +
            "VALUES (DEFAULT, ?, ?, ?, ?, ?, DEFAULT, ?, ?, ?)";
    String CREATE_SECURITY = "INSERT INTO security (id, login, password, role, created, updated, user_id) " +
            "VALUES (DEFAULT, ?, ?, ?, DEFAULT, ?, ?)";
    String ADD_PRODUCT_BY_USER = "INSERT INTO l_users_product (product_id, user_id) VALUES (?, ?)";
    String FIND_BY_ID_HQL = "from users where id = :userId";
    String FIND_ALL_HQL = "from users";
    String DELETE_BY_ID_HQL = "delete from users where id = :id";
    String COPY_USER_HQL = "insert into users(firstname, secondName, age, email, sex, telephoneNumber, isDeleted, updated, created) select firstname, secondName, age, email, sex, telephoneNumber, isDeleted, updated, created from users_second where users_second.id = :id";
    String UPDATE_USER_BY_ID_HQL = "update users set firstname = :firstname, secondName = :secondName, age = :age," +
            "email = :email,sex = :sex,telephoneNumber = :telephoneNumber,updated = :updated,isDeleted = :isDeleted where id = :id";
}


