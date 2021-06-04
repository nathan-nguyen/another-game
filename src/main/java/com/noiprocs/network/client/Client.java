package com.noiprocs.network.client;

import com.noiprocs.network.CommunicationManager;
import com.noiprocs.network.SenderInterface;

import java.io.IOException;
import java.net.Socket;

public class Client implements SenderInterface {
    private final CommunicationManager communicationManager = new CommunicationManager();
    private ClientOutStreamRunnable clientOutStreamRunnable;

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

            clientOutStreamRunnable = new ClientOutStreamRunnable(socket);
            Thread clientOutputThread = new Thread(clientOutStreamRunnable);
            clientOutputThread.start();

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



    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("java Client <ipAddress> <portNumber>");
            return;
        }

        Client client = new Client(args[0], Integer.parseInt(args[1]));
        client.startService();
    }

    @Override
    public void sendMessage(String message) {
        clientOutStreamRunnable.sendMessage(message);
    }
}