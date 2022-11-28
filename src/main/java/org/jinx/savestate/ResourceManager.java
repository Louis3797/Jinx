package org.jinx.savestate;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ResourceManager implements Serializable{
    public static final long serialVersionUID = 42L;

    /**
     * Saves data in savefile
     * @param data Gamedata
     * @param filename Filename
     */
    public static void save(Serializable data, String filename){
        try(ObjectOutputStream oos = new ObjectOutputStream(Files.newOutputStream(Paths.get(filename)))){
            oos.writeObject(data);
        }
        catch (Exception e){

        }
    }

    /**
     * Loads data in to game
     * @param filename Filename
     * @return Gamedata
     * @throws Exception
     */
    public static Object load(String filename) throws Exception{
        try(ObjectInputStream ois = new ObjectInputStream(Files.newInputStream(Paths.get(filename)))){
            return ois.readObject();
        }
    }
}
