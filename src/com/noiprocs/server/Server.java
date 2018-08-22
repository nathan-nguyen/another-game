package com.noiprocs.server;

import com.noiprocs.core.WorldContainer;
import com.noiprocs.server.loader.GameLoader;
import com.noiprocs.server.loader.GameLoaderInterface;
import com.noiprocs.server.network.ServerInStreamRunnable;
import com.noiprocs.server.network.ServerOutStreamRunnable;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

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

            // Close server socket on JVM termination
            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                try {
                    serverSocket.close();
                    this.saveGame();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                System.out.println("Shutting down server!");
            }));

            while (!serverSocket.isClosed()) {
                try {
                    Socket socket = serverSocket.accept();
                    System.out.println("Client connected!");
                    Thread serverInStreamThread = new Thread(new ServerInStreamRunnable(socket));
                    serverInStreamThread.start();

                    Thread serverOutStreamThread = new Thread(new ServerOutStreamRunnable(socket));
                    serverOutStreamThread.start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
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