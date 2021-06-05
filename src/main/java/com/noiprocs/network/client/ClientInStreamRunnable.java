package com.noiprocs.network.client;

import com.noiprocs.network.CommunicationManager;

import java.io.*;
import java.net.Socket;

// TODO: Destroy when server disconnects
public class ClientInStreamRunnable implements Runnable {
    private final CommunicationManager communicationManager;
    private ObjectInputStream inputStream;

    public ClientInStreamRunnable(Socket socket, CommunicationManager communicationManager) {
        this.communicationManager = communicationManager;
        try {
            inputStream = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            while (true) {
                communicationManager.receiveMessage(hashCode(), inputStream.readObject());
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Server disconnected");
            communicationManager.serverDisconnect();
        }
    }
}
