package com.noiprocs.network.client;

import com.noiprocs.network.CommunicationManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

// TODO: Destroy when server disconnects
public class ClientInStreamRunnable implements Runnable {
    private CommunicationManager communicationManager;
    private BufferedReader mBufferReader;

    public ClientInStreamRunnable(Socket socket, CommunicationManager communicationManager) {
        this.communicationManager = communicationManager;
        try {
            mBufferReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            String answer;
            while (true) {
                answer = mBufferReader.readLine();
                if (answer == null) {
                    System.out.println("Server disconnected");
                    communicationManager.serverDisconnect();
                    break;
                } else communicationManager.receiveMessage(hashCode(), answer);
            }
        } catch (Exception e) {
            e.printStackTrace();
            communicationManager.serverDisconnect();
        }
    }
}
