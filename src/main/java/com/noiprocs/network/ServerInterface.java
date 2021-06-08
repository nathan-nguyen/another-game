package com.noiprocs.network;

public interface ServerInterface extends SenderInterface {
    void sendMessage(int clientId, Object object);
}
