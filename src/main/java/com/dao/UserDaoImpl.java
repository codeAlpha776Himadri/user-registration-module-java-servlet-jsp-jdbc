package com.dao;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;

import com.entities.User;

public class UserDaoImpl implements UserDao {

    private JdbcTemplate jdbcTemplate;

    @Override
    public void addUser(User user) {
        String query = "insert into users(user_name,user_password,user_email) values(?,?,?);";
        String userName = user.getUserName().toLowerCase();
        String userPassword = user.getUserPassword();
        String userEmail = user.getUserEmail();
        jdbcTemplate.update(query, userName, userPassword, userEmail);
    }

    @Override
    public List<User> getUserByEmail(String email) {

        String query = "select user_id,user_name, user_password, user_email from users where user_email=? ;";
        return this.jdbcTemplate.query(query, new RowMapperImpl(), email);

    }

    @Override
    public List<User> getAllUsers() {
        String query = "select * from users ;";
        return this.jdbcTemplate.query(query, new RowMapperImpl());
    }

    public UserDaoImpl(JdbcTemplate jdbcTemplate) {
        super();
        this.jdbcTemplate = jdbcTemplate;
    }

    public UserDaoImpl() {
        super();
    }

}
