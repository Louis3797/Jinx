package org.jinx.login;


import org.jinx.wrapper.SafeScanner;
import java.io.*;
import java.util.*;
import java.util.logging.Logger;
import org.jinx.login.AES;

import static org.jinx.utils.ConsoleColor.BLUE;
import static org.jinx.utils.ConsoleColor.RESET;

public class Login implements Serializable{
    private transient final SafeScanner safeScanner;
    private final List<String> loginlist;

    private final String secretKey = "secretKey";

    private transient final Logger LOGGER = Logger.getLogger(Login.class.getName());

    public Login() {
        safeScanner = new SafeScanner();
        loginlist = new ArrayList<>();
    }

    /**
     * Method to register player in tetxfile
     */
    public void register() {

        readLoginandPass();
        System.out.println(BLUE + "Benutzername:" + RESET);
        String playerName = safeScanner.nextStringSafe();

        if (doesUserExist(playerName)) {
            System.out.println(BLUE + "Benutzer " + playerName + " existiert bereits.\nBitte geben sie ein anderen Namen ein:" + RESET);
            register();
        }
        else {
            System.out.println(BLUE + "Passwort:" + RESET);
            String password = safeScanner.nextStringSafe();
            String enc = AES.encrypt(password, secretKey);

            try {
                BufferedWriter login = new BufferedWriter(new FileWriter("login.txt", true));
                login.write(playerName + "," + enc);
                login.newLine();
                login.close();

            } catch (IOException e) {
                LOGGER.warning(e.getMessage());
            }
        }
    }

    /**
     * Method to login to System
     * @return if input is correct return username, if input is wrong return empty string
     */
    public String loginSystem() {

        readLoginandPass();
        System.out.println(BLUE + "Benutzername:" + RESET);
        String userName = safeScanner.nextStringSafe();
        System.out.println(BLUE + "Passwort:" + RESET);
        String pass = safeScanner.nextStringSafe();

        if (doesUserandPassExist(userName, pass)) {
            return userName;
        }

        return "";
    }

    /**
     * Method to read login and password from textfile
     */
    private void readLoginandPass() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("Login.txt"));
            String line;

            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                loginlist.add(data[0]);
                loginlist.add(data[1]);
            }

        } catch (IOException e) {
            LOGGER.warning(e.getMessage());
        }
    }

    /**
     * convert arrayList to HashMap
     * @param arrayList
     * @return haspMap
     */
    private HashMap<String, String> convertArrayListToHashMap(ArrayList<String> arrayList) {
        HashMap<String, String> hashMap = new HashMap<>();
        for (int i = 0; i < loginlist.size() - 1; i += 2) {
            hashMap.put(loginlist.get(i), loginlist.get(i + 1));
        }

        return hashMap;
    }

    /**
     * Method to check user if he exists in textfile
     * @param login username
     * @return false if user doesn't exist
     */
    private boolean doesUserExist(String login) {
        for (String user : loginlist) {
            if (user.equals(login))
                return true;
        }
        return false;
    }

    /**
     * Method to check user if he exists in textfile
     * @param login username and password
     * @return false if user and password doesn't exist in textfile, return true if user and password exist in textfile
     */
    private boolean doesUserandPassExist(String login, String pass) {
        HashMap<String, String> loginMap = convertArrayListToHashMap((ArrayList<String>) loginlist);
        for (Map.Entry<String, String> entry : loginMap.entrySet()) {
            if (entry.getKey().equals(login) && Objects.equals(AES.decrypt(entry.getValue(), secretKey), pass)) {
                return true;
            }
        }

        return false;
    }

}




