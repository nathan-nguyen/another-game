package com.noiprocs.server;

import com.noiprocs.core.WorldContainer;
import com.noiprocs.server.loader.GameLoader;
import com.noiprocs.server.loader.GameLoaderInterface;

import java.io.IOException;
import java.net.ServerSocket;

public class Server {
    private int mPortNumber;
    private GameLoaderInterface mGameLoader = new GameLoader();
    private WorldContainer mWorldContainer;

    public Server(int mPortNumber) {
        this.mPortNumber = mPortNumber;
    }

    private void run() {
        Object loadObject = mGameLoader.loadGame();
        if (loadObject != null) {
            mWorldContainer = (WorldContainer) mGameLoader.loadGame();
        } else {
            mWorldContainer = new WorldContainer();
        }

        this.startService();
        this.saveGame();
    }

    private void startService() {
        try {
            ServerSocket serverSocket = new ServerSocket(mPortNumber);

            while (true) {
                ClientThread clientThread = new ClientThread(serverSocket.accept());
                Thread t = new Thread(clientThread);
                t.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveGame() {
        mGameLoader.saveGame(mWorldContainer);
    }

    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Port Number Required\n java Server <portNumber>");
            return;
        }

        Server server = new Server(Integer.parseInt(args[0]));
        server.run();
    }
}