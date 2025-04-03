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
}


