package com.example.tobyspring3.dao;

import com.example.tobyspring3.domain.User;

import java.sql.*;
import java.util.Map;

import static java.lang.System.getenv;

public abstract class UserDao {

    public abstract Connection getConnetion() throws SQLException, ClassNotFoundException; {

    }
    public void add(User user) throws ClassNotFoundException, SQLException {
        Connection conn = getConnetion();
        PreparedStatement pstmt = conn.prepareStatement("insert into users(id , name , password) values (?,?,?)");
        pstmt.setString(1, user.getId());
        pstmt.setString(2, user.getName());
        pstmt.setString(3, user.getPassword());

        pstmt.executeUpdate();
        pstmt.close();
        conn.close();
    }

    public User get(String id) throws ClassNotFoundException, SQLException { // id받아 검색한다.
        Connection conn = getConnetion();
        PreparedStatement pstmt = conn.prepareStatement("select id , name , password from users where id = ?");
        pstmt.setString(1,id);
        ResultSet rs = pstmt.executeQuery();
        rs.next();

        User user = new User();
        user.setId(rs.getString("id"));
        user.setName(rs.getString("name"));
        user.setPassword(rs.getString("password"));

        rs.close();
        pstmt.close();
        conn.close();

        return user;
    }

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        UserDao userDao = new NUserDao();
        User user = new User();
        user.setId("2");
        user.setName("dsa");
        user.setPassword("1234");
        //userDao.add(user);

        User selectedUser = userDao.get("2"); // 검색
        System.out.println(selectedUser.getId());
        System.out.println(selectedUser.getName());
        System.out.println(selectedUser.getPassword());
    }
}
