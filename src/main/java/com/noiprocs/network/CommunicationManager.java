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

    public void receiveMessage(int clientId, Object object) {
        receiver.receiveMessage(clientId, object);
    }

    public void setSender(SenderInterface mSender) {
        this.mSender = mSender;
    }

    public void setReceiver(ClientInterface receiver) {
        this.receiver = receiver;
    }

    public void sendMessage(Object object){
        mSender.sendMessage(object);
    }
}
