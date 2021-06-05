package com.noiprocs.network.client;

import com.noiprocs.network.CommunicationManager;
import com.noiprocs.network.SenderInterface;

import java.io.IOException;
import java.net.Socket;

public class Client implements SenderInterface {
    private final CommunicationManager communicationManager = new CommunicationManager();
    private ClientOutStream clientOutStream;

    private final String hostName;
    private final int port;

    public Client(String hostName, int port) {
        this.hostName = hostName;
        this.port = port;
        communicationManager.setSender(this);
    }

    public void startService() {
        try {
            Socket socket = new Socket(hostName, port);
            System.out.println("Connected to Server");

            ClientInStreamRunnable clientInStreamRunnable = new ClientInStreamRunnable(socket, communicationManager);
            Thread clientInputThread = new Thread(clientInStreamRunnable);
            clientInputThread.start();

            clientOutStream = new ClientOutStream(socket);

            // Close socket on JVM termination
            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                System.out.println("Disconnected from server!");
            }));
        } catch (IOException e) {
            System.out.println("Cannot connect to server " + hostName + ":" + port);
            e.printStackTrace();
        }
    }

    public CommunicationManager getCommunicationManager() {
        return communicationManager;
    }

    @Override
    public void sendMessage(Object object) {
        clientOutStream.sendMessage(object);
    }
}