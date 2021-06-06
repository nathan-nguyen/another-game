package com.noiprocs.network;

public interface ReceiverInterface {
    /**
     * For both client and server
     * @param source: Sender hashcode
     * @param object: Object content
     */
    void receiveMessage(int source, Object object);

    /**
     * For client only
     */
    void serverDisconnect();

    /**
     * For server only
     * @param clientId: Client hashcode
     */
    void clientConnect(int clientId);

    /**
     * For server only
     * @param clientId: Client hashcode
     */
    void clientDisconnect(int clientId);
}
