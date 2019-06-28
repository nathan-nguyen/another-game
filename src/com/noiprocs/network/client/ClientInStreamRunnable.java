package com.noiprocs.network.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ClientInStreamRunnable implements Runnable {
    private BufferedReader mBufferReader;

    public ClientInStreamRunnable(Socket socket) {
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
                    break;
                } else {
                    System.out.println("[Server]: " + answer);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
