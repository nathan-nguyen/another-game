package com.noiprocs.server.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ServerInStreamRunnable implements Runnable {
    private BufferedReader mBufferReader;

    public ServerInStreamRunnable(Socket socket) {
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
                    break;
                } else {
                    System.out.println("[Client] :" + answer);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
