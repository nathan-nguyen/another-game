package com.noiprocs.network.server;

import com.noiprocs.network.CommunicationManager;
import com.noiprocs.network.Config;
import com.noiprocs.network.ServerInterface;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ClientListenerRunnable implements Runnable, ServerInterface {
    private final CommunicationManager mCommunicationManager;
    private final Map<Integer, ServerOutStream> serverOutStreamMap = new HashMap<>();
    private final List<Integer> removeClientIdList = new ArrayList<>();


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
                    ServerInStreamRunnable serverInStreamRunnable = new ServerInStreamRunnable(
                            socket, mCommunicationManager
                    );

                    int clientId = serverInStreamRunnable.hashCode();
                    mCommunicationManager.clientConnect(clientId);

                    Thread serverInStreamThread = new Thread(serverInStreamRunnable);
                    serverInStreamThread.start();

                    serverOutStreamMap.put(clientId, new ServerOutStream(socket));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void sendMessage(Object object) {
        removeClientIdList.clear();
        serverOutStreamMap.forEach((clientId, serverOutStream) -> {
            try {
                serverOutStream.sendMessage(object);
            } catch (IOException e) {
                e.printStackTrace();
                removeClientIdList.add(clientId);
            }
        });

        removeClientIdList.forEach(serverOutStreamMap::remove);
    }

    @Override
    public void sendMessage(int clientId, Object object) {
        try {
            serverOutStreamMap.get(clientId).sendMessage(object);
        } catch (IOException e) {
            e.printStackTrace();
            serverOutStreamMap.remove(clientId);
        }
    }
}
