package com.noiprocs.network.server;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

// TODO: Handle case to destroy when client disconnects
public class ServerOutStreamRunnable implements Runnable {
    private OutputStream outputStream;

    public ServerOutStreamRunnable(Socket socket) {
        try {
            this.outputStream = socket.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {}

    public void sendMessage(byte[] bytes) {
        try {
            outputStream.write(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
