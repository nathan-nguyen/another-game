package com.noiprocs.server.loader;

import com.noiprocs.server.Config;

import java.io.*;

public class GameLoader implements GameLoaderInterface {
    @Override
    public void saveGame(Object o) {
        try {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(new File(Config.SAVE_FILE)));
            objectOutputStream.writeObject(o);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Object loadGame() {
        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(new File(Config.SAVE_FILE)));
            return objectInputStream.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
