package com.noiprocs.network.client;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

public class ClientOutStream {
    private OutputStream outputStream;

    public ClientOutStream(Socket socket) {
        try {
            outputStream = socket.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(Object object) {
        try {
            byte[] bytes = ((String) object).getBytes();
            outputStream.write(bytes);
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
