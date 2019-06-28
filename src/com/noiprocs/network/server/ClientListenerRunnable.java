package com.noiprocs.network.server;

import com.noiprocs.network.Config;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ClientListenerRunnable implements Runnable {
    private CommunicationManager mCommunicationManager;
    private List<ServerOutStreamRunnable> serverOutStreamRunnableList = new ArrayList<>();


    public ClientListenerRunnable(CommunicationManager mCommunicationManager) {
        this.mCommunicationManager = mCommunicationManager;
        MessageSenderInterface senderImplementation = new SenderImplementation();
        mCommunicationManager.setSender(senderImplementation);
    }

    @Override
    public void run() {
        try {
            ServerSocket serverSocket = new ServerSocket(Config.PORT);

            // Close server socket on JVM termination
            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                try {
                    serverSocket.close();
                    // Do last action here!
                } catch (IOException e) {
                    e.printStackTrace();
                }
                System.out.println("Shutting down server!");
            }));

            while (!serverSocket.isClosed()) {
                try {
                    Socket socket = serverSocket.accept();
                    System.out.println("Client connected!");
                    ServerInStreamRunnable serverInStreamRunnable = new ServerInStreamRunnable(socket, mCommunicationManager);
                    Thread serverInStreamThread = new Thread(serverInStreamRunnable);
                    serverInStreamThread.start();

                    ServerOutStreamRunnable serverOutStreamRunnable = new ServerOutStreamRunnable(socket);
                    serverOutStreamRunnableList.add(serverOutStreamRunnable);
                    Thread serverOutStreamThread = new Thread(serverOutStreamRunnable);
                    serverOutStreamThread.start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    class SenderImplementation implements MessageSenderInterface {
        public void sendMessage(String message) {
            for (ServerOutStreamRunnable serverOutStreamRunnable: serverOutStreamRunnableList) {
                serverOutStreamRunnable.sendMessage(message);
            }
        }
    }
}
