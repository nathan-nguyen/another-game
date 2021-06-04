package com.noiprocs.network;

public interface ClientInterface {
    void receiveMessage(int source, String message);
    void serverDisconnect();
    void clientConnectionNotify(int clientId);
    void clientDisconnect(int clientId);
}
