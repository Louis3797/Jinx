package org.jinx.databanklogin;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DataConnection {

    public static Connection getConnection() {

        Connection connection = null;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/mysql_database", "root", "admin");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return connection;
    }

    public boolean checkUsername(String username) {
        PreparedStatement ps;
        ResultSet rs;
        boolean checkUser = false;
        String query = "SELECT * FROM `user` WHERE `username` =?";

        try {
            ps = DataConnection.getConnection().prepareStatement(query);
            ps.setString(1, username);

            rs = ps.executeQuery();

            if (rs.next()) {
                checkUser = true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(DataConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        return checkUser;
    }

    public boolean checkPassword(String username,String password) {
        PreparedStatement ps;
        ResultSet rs;
        boolean checkUser = false;
        String query = "SELECT * FROM `user` WHERE `username` =? and pass = md5(?)";

        try {
            ps = DataConnection.getConnection().prepareStatement(query);
            ps.setString(1, username);
            ps.setString(2,password);
            rs = ps.executeQuery();

            if (rs.next()) {
                checkUser = true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(DataConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        return checkUser;
    }

}
