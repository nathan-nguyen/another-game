package com.noiprocs.network.server;

import com.noiprocs.network.CommunicationManager;
import com.noiprocs.network.Config;
import com.noiprocs.network.SenderInterface;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

// TODO: Handle case when client disconnects, remove from serverOutStreamRunnableList
public class ClientListenerRunnable implements Runnable, SenderInterface {
    private final CommunicationManager mCommunicationManager;
    private final List<ServerOutStreamRunnable> serverOutStreamRunnableList = new ArrayList<>();


    public ClientListenerRunnable(CommunicationManager mCommunicationManager) {
        this.mCommunicationManager = mCommunicationManager;
        mCommunicationManager.setSender(this);
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
                    ServerInStreamRunnable serverInStreamRunnable = new ServerInStreamRunnable(socket, mCommunicationManager);
                    mCommunicationManager.clientConnectionNotify(serverInStreamRunnable.hashCode());
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

    @Override
    public void sendMessage(String message) {
        for (ServerOutStreamRunnable serverOutStreamRunnable: serverOutStreamRunnableList) {
            serverOutStreamRunnable.sendMessage(message);
        }
    }

}
