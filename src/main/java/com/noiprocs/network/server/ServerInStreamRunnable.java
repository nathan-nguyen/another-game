package com.noiprocs.network.server;

import com.noiprocs.network.CommunicationManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

// TODO: Handle case to destroy when client disconnects
public class ServerInStreamRunnable implements Runnable {
    private final CommunicationManager mCommunicationManager;
    private BufferedReader mBufferReader;

    public ServerInStreamRunnable(Socket socket, CommunicationManager mCommunicationManager) {
        this.mCommunicationManager = mCommunicationManager;
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
                    System.out.println("Client disconnected");
                    mCommunicationManager.clientDisconnect(this.hashCode());
                    break;
                }
                else mCommunicationManager.receiveMessage(this.hashCode(), answer);
            }
        } catch (Exception e) {
            e.printStackTrace();
            mCommunicationManager.clientDisconnect(this.hashCode());
        }
    }
}
