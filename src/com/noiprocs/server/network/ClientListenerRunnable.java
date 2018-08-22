package com.noiprocs.server.network;

import java.io.IOException;
import java.net.ServerSocket;

public class ClientListenerRunnable implements Runnable {
    private ServerSocket mServerSocket;

    public ClientListenerRunnable (ServerSocket mServerSocket) {
        this.mServerSocket = mServerSocket;
    }

    @Override
    public void run() {
        while (!mServerSocket.isClosed()) {
            try {
                Thread t = new Thread(new ClientThreadRunnable(mServerSocket.accept()));
                t.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
