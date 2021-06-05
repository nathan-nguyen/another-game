package com.noiprocs.network;

import com.noiprocs.network.client.Client;
import com.noiprocs.network.server.Server;

import java.util.Scanner;

// Example on how to use this library
public class App {
    public static void main(String[] args) {
        ClientInterface messagePrinter = new ClientInterface() {
            @Override
            public void receiveMessage(int source, Object object) {
                System.out.println("Received message from " + source + " - Content: " + object);
            }

            @Override
            public void serverDisconnect() {
                System.out.println("Server disconnected!");
            }

            @Override
            public void clientConnectionNotify(int clientId) {
                System.out.println("Client " + clientId + " connected!");
            }

            @Override
            public void clientDisconnect(int clientId) {
                System.out.println("Client " + clientId + " disconnected!");
            }
        };

        final CommunicationManager communicationManager;
        if (args.length == 2) {
            System.out.println("Starting client");
            Client client = new Client(args[0], Integer.parseInt(args[1]));
            client.startService();
            communicationManager = client.getCommunicationManager();
        } else {
            System.out.println("Starting server");
            Server server = new Server();
            server.startService();
            server.getCommunicationManager().setReceiver(messagePrinter);
            communicationManager = server.getCommunicationManager();
        }
        communicationManager.setReceiver(messagePrinter);


        Scanner in = new Scanner(System.in);

        String message = in.nextLine();
        while (message != null) {
            try {
                communicationManager.sendMessage(message);
            } catch (Exception e) {
                e.printStackTrace();
            }
            message = in.nextLine();
            System.out.println("Running threads: " + java.lang.Thread.activeCount());
        }
    }
}
