package com.noiprocs.network.server;

import com.noiprocs.network.CommunicationManager;

public class Server {
    private final CommunicationManager communicationManager = new CommunicationManager();

    public void startService() {
        System.out.println("Server started");
        Thread clientListenerThread = new Thread(new ClientListenerRunnable(communicationManager));
        clientListenerThread.start();
    }

    public CommunicationManager getCommunicationManager() {
        return communicationManager;
    }
}