package org.jinx.databanklogin;

import java.io.Serializable;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DataConnection implements Serializable {

    public static final long serialVersionUID = 42L;

    /**
      Method to connect with database
     *
     * @return Connection
     */
    public static Connection getConnection() {

        Connection connection = null;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/mysql_database", "root", "admin");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return connection;
    }

    /**
     * Username validation method with database
     * @param username username of player
     * @return checkUser if user is in database
     */
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
            Logger.getLogger(DataConnection.class.getName()).log(Level.WARNING, ex.getMessage());
        }
        return checkUser;
    }

    /**
     * Username and Password validation method with database
     * @param username username of player
     * @param password password of player
     * @return checkUser if next user is available
     */
    public boolean checkUserandPassword(String username,String password) {
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
            Logger.getLogger(DataConnection.class.getName()).log(Level.WARNING, ex.getMessage());
        }
        return checkUser;
    }

}
