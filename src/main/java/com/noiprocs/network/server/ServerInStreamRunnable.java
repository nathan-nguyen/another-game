package com.noiprocs.network.server;

import com.noiprocs.network.CommunicationManager;

import java.io.*;
import java.net.Socket;

// TODO: Handle case to destroy when client disconnects
public class ServerInStreamRunnable implements Runnable {
    private final CommunicationManager mCommunicationManager;
    private InputStream inputStream;

    public ServerInStreamRunnable(Socket socket, CommunicationManager mCommunicationManager) {
        this.mCommunicationManager = mCommunicationManager;
        try {
            this.inputStream = socket.getInputStream();
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
                    // Notify server that client is disconnected
                    mCommunicationManager.clientDisconnect(this.hashCode());
                    return;
                }

                buffer.write(bytes, 0, count);
                mCommunicationManager.receiveMessage(this.hashCode(), buffer.toByteArray());
                buffer.reset();
            }
        } catch (Exception e) {
            e.printStackTrace();
            // Notify server that client is disconnected
            mCommunicationManager.clientDisconnect(this.hashCode());
        }
    }
}
