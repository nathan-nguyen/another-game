package com.noiprocs.network.client;

import com.noiprocs.network.CommunicationManager;

import java.io.*;
import java.net.Socket;

// TODO: Destroy when server disconnects
public class ClientInStreamRunnable implements Runnable {
    private final CommunicationManager communicationManager;
    private InputStream inputStream;

    public ClientInStreamRunnable(Socket socket, CommunicationManager communicationManager) {
        this.communicationManager = communicationManager;
        try {
            inputStream = socket.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            byte[] bytes = new byte[16384];
            while (true) {
                int count = inputStream.read(bytes);
                if (count == -1) {
                    // Notify client that server is disconnected
                    communicationManager.serverDisconnect();
                    return;
                }

                buffer.write(bytes, 0, count);
                communicationManager.receiveMessage(hashCode(), buffer.toByteArray());
                buffer.reset();
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Server disconnected");
            communicationManager.serverDisconnect();
        }
    }
}
