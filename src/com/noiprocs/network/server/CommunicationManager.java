package com.noiprocs.network.server;

import java.util.Scanner;

public class CommunicationManager {
    private MessageSenderInterface mSender;

    public void receiveMessage(int clientId, String hashCode) {
        System.out.println(clientId + " - " + hashCode);
    }

    public void setSender(MessageSenderInterface mSender) {
        this.mSender = mSender;
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
