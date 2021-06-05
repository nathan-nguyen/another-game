package com.noiprocs.network;

public interface ClientInterface {
    void receiveMessage(int source, Object object);
    void serverDisconnect();
    void clientConnectionNotify(int clientId);
    void clientDisconnect(int clientId);
}
