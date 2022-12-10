package org.jinx.game_state;

import org.jinx.logging_file_handler.LogFileHandler;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.Logger;

public class ResourceManager {
    private static final Logger logger = Logger.getLogger(ResourceManager.class.getName());

    static {
        logger.addHandler(LogFileHandler.getInstance().getFileHandler());
        logger.setUseParentHandlers(false);
    }

    /**
     * Saves data in savefile
     *
     * @param data     Gamedata
     * @param filename Filename
     */
    public static void save(Serializable data, String filename) {
        try (ObjectOutputStream oos = new ObjectOutputStream(Files.newOutputStream(Paths.get(filename)))) {
            oos.writeObject(data);
        } catch (IOException e) {
            logger.warning(e.getMessage());
        }
    }

    /**
     * Loads data in to game
     *
     * @param filename Filename
     * @return Gamedata
     */
    public static Object load(String filename) {
        try {
            ObjectInputStream ois = new ObjectInputStream(Files.newInputStream(Paths.get(filename)));

            return ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            logger.warning(e.getMessage());
        }
        return null;
    }
}
