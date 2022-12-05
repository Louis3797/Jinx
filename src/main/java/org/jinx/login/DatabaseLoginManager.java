package org.jinx.login;

import org.jinx.database.JDBCHelper;
import org.jinx.encryption.AES;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

public class DatabaseLoginManager implements ILoginManager {

    private static final Logger logger = Logger.getLogger(FileLoginManager.class.getName());

    private final String secret = "secretKey";

    public DatabaseLoginManager() {
        try {
            logger.addHandler(new FileHandler("logs.log"));
            logger.setUseParentHandlers(false);
        } catch (IOException e) {
            logger.warning(e.getMessage());
        }
    }

    @Override
    public boolean checkCredentials(String username, String password) {

        Connection con = JDBCHelper.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        String query = "SELECT * FROM `user` WHERE `username` =? and pass = md5(?)";

        try {
            preparedStatement = con.prepareStatement(query);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, AES.encrypt(password, secret));
            rs = preparedStatement.executeQuery();

            if (rs.next()) {
                logger.info("Found user " + username);
                return true;
            }
        } catch (SQLException ex) {
            logger.warning(ex.getMessage());
            return false;
        } finally {
            JDBCHelper.closePreparedStatement(preparedStatement);
            JDBCHelper.closeResultSet(rs);
            JDBCHelper.closeConnection(con);
        }

        logger.info("User " + username + " doesnt exist");
        return false;

    }

    @Override
    public boolean registerNewUser(String username, String password) {


        if (doesUserExist(username)) {
            System.out.println("Der Spieler " + username + " existiert bereits schon.");

            logger.info("Der Spieler " + username + " existiert bereits schon.");
            return false;
        }

        Connection con = JDBCHelper.getConnection();
        PreparedStatement preparedStatement = null;

        try {
            preparedStatement = con.prepareStatement("INSERT INTO " +
                    "`user`(`username`, `pass`) VALUES (?,MD5(?))");
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, AES.encrypt(password, secret));
            preparedStatement.executeUpdate();
            logger.info("User + " + username + " was successfully registered");
            return true;
        } catch (SQLException e) {
            logger.warning(e.getMessage());
            return false;
        } finally {
            JDBCHelper.closePreparedStatement(preparedStatement);
            JDBCHelper.closeConnection(con);
        }
    }

    @Override
    public boolean doesUserExist(String username) {
        Connection con = JDBCHelper.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        String query = "SELECT * FROM `user` WHERE `username` =?";

        try {
            preparedStatement = con.prepareStatement(query);

            preparedStatement.setString(1, username);

            rs = preparedStatement.executeQuery();

            if (rs.next()) {
                return true;
            }
        } catch (SQLException e) {
            logger.warning(e.getMessage());
            return false;
        } finally {
            JDBCHelper.closePreparedStatement(preparedStatement);
            JDBCHelper.closeResultSet(rs);
            JDBCHelper.closeConnection(con);
        }
        return false;
    }
}
