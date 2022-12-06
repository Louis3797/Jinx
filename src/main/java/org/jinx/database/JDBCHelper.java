package org.jinx.database;

import java.sql.*;
import java.util.logging.Logger;

public class JDBCHelper {
    private static final Logger logger = Logger.getLogger(JDBCHelper.class.getName());

    public JDBCHelper() {
    }

    /**
     * Returns Connection to the database
     *
     * @return Returns a Connection Object with connection with the database
     */
    public static Connection getConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(DatabaseConstants.URL, DatabaseConstants.USER, DatabaseConstants.PASSWORD);
        } catch (SQLException e) {
            logger.warning(e.getMessage());
            System.out.println("Verbindung zur Datenbank konnte nicht hergestellt werden");
        }

        return connection;
    }

    /**
     * Closes Connection to database
     *
     * @param con Open Connection to the Database
     */
    public static void closeConnection(Connection con) {
        if (con != null) {
            try {
                con.close();
            } catch (SQLException e) {
                logger.warning(e.getMessage());
            }
        }
    }

    /**
     * Closes given PreparedStatement object
     *
     * @param stmt given prepared statement
     */
    public static void closePreparedStatement(PreparedStatement stmt) {
        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException e) {
                logger.warning(e.getMessage());
            }
        }
    }

    /**
     * Closes given ResultSet
     *
     * @param rs Given ResultSet
     */
    public static void closeResultSet(ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                logger.warning(e.getMessage());
            }
        }
    }
}
