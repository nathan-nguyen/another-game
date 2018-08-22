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
    }

    private void startService() {
        try {
            ServerSocket serverSocket = new ServerSocket(mPortNumber);
            Thread clientListener = new Thread(new ClientListenerRunnable(serverSocket));
            clientListener.start();

            // Close server socket on JVM termination
            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                try {
                    serverSocket.close();
                    this.saveGame();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                System.out.println("The server is shut down!");
            }));
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