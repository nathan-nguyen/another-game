package com.noiprocs.network.client;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

// TODO: Handle case to destroy when server disconnect
public class ClientOutStreamRunnable implements Runnable {
    private OutputStream outputStream;

    public ClientOutStreamRunnable(Socket socket) {
        try {
            outputStream = socket.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
    }

    public void sendMessage(byte[] bytes) {
        try {
            outputStream.write(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
