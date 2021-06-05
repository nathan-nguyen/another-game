package com.noiprocs.network.server;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ServerOutStream {
    private final ObjectOutputStream outputStream;

    public ServerOutStream(Socket socket) throws IOException {
        this.outputStream = new ObjectOutputStream(socket.getOutputStream());
    }

    public void sendMessage(Object object) throws IOException {
        outputStream.writeObject(object);
        outputStream.flush();
        outputStream.reset();
    }
}
