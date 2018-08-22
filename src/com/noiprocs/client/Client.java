package com.noiprocs.client;

import com.noiprocs.client.network.ClientInputRunnable;
import com.noiprocs.client.network.ClientOutputRunnable;

import java.io.IOException;
import java.net.Socket;

public class Client {
    private String mHostName;
    private int mPortNumber;

    public Client(String mHostName, int mPortNumber) {
        this.mHostName = mHostName;
        this.mPortNumber = mPortNumber;
    }

    private void run() {
        this.startService();
    }

    private void startService() {
        try {
            Socket socket = new Socket(mHostName, mPortNumber);
            System.out.println("Connected to Server");

            Thread clientInputThread = new Thread(new ClientInputRunnable(socket));
            clientInputThread.start();

            Thread clientOutputThread = new Thread(new ClientOutputRunnable(socket));
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
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("java Client <ipAddress> <portNumber>");
            return;
        }

        Client client = new Client(args[0], Integer.parseInt(args[1]));
        client.run();
    }
}