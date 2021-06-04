package com.noiprocs.network;

public interface ClientInterface {
    void receiveMessage(int source, byte[] bytes);
    void serverDisconnect();
    void clientConnectionNotify(int clientId);
    void clientDisconnect(int clientId);
}
