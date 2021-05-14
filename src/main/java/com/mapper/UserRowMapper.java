package com.mapper;

import com.model.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class UserRowMapper implements RowMapper<User> {
    private static final DateTimeFormatter DT_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");


    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {

        return User.builder()
                .id(rs.getInt("id"))
                .name(rs.getString("name"))
                .dept(rs.getString("dept"))
                .salary(rs.getInt("salary"))
                .birthdate(LocalDateTime.parse(rs.getString("birthdate"), DT_FORMAT))
                .build();
    }
}