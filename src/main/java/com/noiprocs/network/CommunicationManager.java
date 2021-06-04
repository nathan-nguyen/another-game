package com.noiprocs.network;

import java.util.Scanner;

public class CommunicationManager {
    private SenderInterface mSender;
    private ClientInterface receiver;

    public void serverDisconnect() {
        receiver.serverDisconnect();
    }

    public void clientConnectionNotify(int clientId) {
        System.out.println("Client " + clientId + " connected!");
        receiver.clientConnectionNotify(clientId);
    }

    public void clientDisconnect(int clientId) {
        System.out.println("Client " + clientId + " dis-connected!");
        receiver.clientDisconnect(clientId);
    }

    public void receiveMessage(int clientId, String message) {
        receiver.receiveMessage(clientId, message);
    }

    public void setSender(SenderInterface mSender) {
        this.mSender = mSender;
    }

    public void setReceiver(ClientInterface receiver) {
        this.receiver = receiver;
    }

    public void sendMessage(String message) {
        mSender.sendMessage(message);
    }

    public void start() {
        Scanner in = new Scanner(System.in);

        String message = in.nextLine();
        while (message != null) {
            mSender.sendMessage(message);
            message = in.nextLine();
        }
    }
}
