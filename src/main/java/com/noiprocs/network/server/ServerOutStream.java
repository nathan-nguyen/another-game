package com.noiprocs.network.server;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

public class ServerOutStream {
    private OutputStream outputStream;

    public ServerOutStream(Socket socket) throws IOException {
        this.outputStream = socket.getOutputStream();
    }

    public void sendMessage(byte[] bytes) throws IOException {
        outputStream.write(bytes);
    }
}
