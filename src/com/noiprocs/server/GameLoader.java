package com.noiprocs.server;

import java.io.*;

public class GameLoader {
    public void saveGame(Object o) {
        try {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(new File(Config.SAVE_FILE)));
            objectOutputStream.writeObject(o);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

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
