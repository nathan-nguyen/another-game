package com.noiprocs.network.server;

public class Server {
    private CommunicationManager mCommunicationManager = new CommunicationManager();

    private void run() {
        this.startService();
    }

    private void startService() {
        System.out.println("Server started");
        Thread clientListenerThread = new Thread(new ClientListenerRunnable(mCommunicationManager));
        clientListenerThread.start();
        mCommunicationManager.start();
    }

    public static void main(String[] args) {
        Server server = new Server();
        server.run();
    }
}