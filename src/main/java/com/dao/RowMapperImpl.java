package com.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.entities.User;

public class RowMapperImpl implements RowMapper<User> {

    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        User user = new User() ;
        user.setUserName(rs.getString(2));
        user.setUserPassword(rs.getString(3));
        user.setUserEmail(rs.getString(4));
        return user ;
    }
    
}
