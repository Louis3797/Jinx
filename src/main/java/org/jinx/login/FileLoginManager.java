package org.jinx.login;

import org.jinx.encryption.AES;
import org.jinx.logging_file_handler.LogFileHandler;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.logging.Logger;

public class FileLoginManager implements ILoginManager {

    private static final Logger logger = Logger.getLogger(FileLoginManager.class.getName());

    /**
     * Name of the login file
     */
    private final String loginFileName = "Login.txt";

    /**
     * Key for encryption
     */
    private final String secret = "secretKey";

    public FileLoginManager() {
        logger.addHandler(LogFileHandler.getInstance().getFileHandler());
        logger.setUseParentHandlers(false);

    }

    @Override
    public boolean checkCredentials(String username, String password) {
        try {
            List<String> credentials = Files.readAllLines(Path.of(loginFileName));

            for (String c : credentials) {
                String[] data = c.split(",");

                String decryptedPassword = AES.decrypt(data[1], secret);

                if (username.equals(data[0]) && password.equals(decryptedPassword)) {
                    logger.info("Found user " + username);
                    return true;
                }
            }
        } catch (IOException e) {
            logger.warning(e.getMessage());
            return false;
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
        String encryptedPassword = AES.encrypt(password, secret);
        try {
            FileWriter file = new FileWriter(loginFileName, true);

            file.append(username).append(",").append(encryptedPassword).append("\n");

            file.close();

            logger.info("User " + username + " was successfully registered");
            return true;
        } catch (IOException e) {
            logger.warning(e.getMessage());
            return false;
        }
    }

    @Override
    public boolean doesUserExist(String username) {
        try {
            List<String> credentials = Files.readAllLines(Path.of("Login.txt"));

            for (String c : credentials) {
                String playerUserName = c.split(",")[0];

                if (username.equals(playerUserName)) {
                    return true;
                }
            }
        } catch (IOException e) {
            logger.warning(e.getMessage());
            return false;
        }

        return false;
    }
}
