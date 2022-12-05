package org.jinx.login;

import java.io.Serializable;

public interface ILoginManager extends Serializable {

    /**
     * Checks credentials of given user in data storage
     * @param username username credential
     * @param password password credential
     * @return Returns true if user exists
     */
    boolean checkCredentials(String username, String password);

    /**
     * Registers user and stores credential in data storage
     * @param username username credential
     * @param password password credential
     * @return Returns true was successfully registered
     */
    boolean registerNewUser(String username, String password);

    /**
     * Checks if user occurs in data storage
     * @param username Given username
     * @return Returns true if user exists
     */
    boolean doesUserExist(String username);
}
