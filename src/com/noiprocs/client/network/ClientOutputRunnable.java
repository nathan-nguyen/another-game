package com.noiprocs.client.network;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ClientOutputRunnable implements Runnable {
    private PrintWriter mPrintWriter;

    public ClientOutputRunnable(Socket socket) {
        try {
            mPrintWriter = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        Scanner in = new Scanner(System.in);

        String message = in.nextLine();
        while (message != null) {
            mPrintWriter.println(message);
            message = in.nextLine();
        }
    }
}
