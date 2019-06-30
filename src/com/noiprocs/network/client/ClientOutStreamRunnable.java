package com.noiprocs.network.client;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

// TODO: Handle case to destroy when server disconnect
public class ClientOutStreamRunnable implements Runnable {
    private PrintWriter mPrintWriter;

    public ClientOutStreamRunnable(Socket socket) {
        try {
            mPrintWriter = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
    }

    public void sendMessage(String message) {
        mPrintWriter.println(message);
    }
}
