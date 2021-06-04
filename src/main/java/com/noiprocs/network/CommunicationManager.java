package com.noiprocs.network;

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
        System.out.println("Client " + clientId + " disconnected!");
        receiver.clientDisconnect(clientId);
    }

    public void receiveMessage(int clientId, byte[] bytes) {
        receiver.receiveMessage(clientId, bytes);
    }

    public void setSender(SenderInterface mSender) {
        this.mSender = mSender;
    }

    public void setReceiver(ClientInterface receiver) {
        this.receiver = receiver;
    }

    public void sendMessage(byte[] bytes) {
        mSender.sendMessage(bytes);
    }
}
