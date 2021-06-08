package com.noiprocs.network;

public class CommunicationManager {
    private SenderInterface sender;
    private ReceiverInterface receiver;

    public void serverDisconnect() {
        receiver.serverDisconnect();
    }

    public void clientConnect(int clientId) {
        System.out.println("Client " + clientId + " connected!");
        receiver.clientConnect(clientId);
    }

    public void clientDisconnect(int clientId) {
        System.out.println("Client " + clientId + " disconnected!");
        receiver.clientDisconnect(clientId);
    }

    public void receiveMessage(int clientId, Object object) {
        receiver.receiveMessage(clientId, object);
    }

    public void setSender(SenderInterface mSender) {
        this.sender = mSender;
    }

    public void setReceiver(ReceiverInterface receiver) {
        this.receiver = receiver;
    }

    public void sendMessage(Object object){
        sender.sendMessage(object);
    }

    public void sendMessage(int clientId, Object object) {
        ((ServerInterface) sender).sendMessage(clientId, object);
    }
}
