package com.noiprocs.network.server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

// TODO: Handle case to destroy when client disconnects
public class ServerOutStreamRunnable implements Runnable {
    private PrintWriter mPrintWriter;

    public ServerOutStreamRunnable(Socket socket) {
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
