package com.network;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

/**
 * Created by azkei on 16/03/2017.
 */
public class Client extends Network implements Runnable {
    private String hostName;
    private int serverPort;
    private Thread thread;

    public Client(final String host, final int port){
        super("CLIENT");
        hostName = host;
        serverPort = port;
        connectToServer();
        thread = new Thread(this,"Client Thread");
        thread.start();
    }

    public void run() {
        System.out.println("CLIENT: Thread communication working");
        try{
            getStreams();
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    private void connectToServer() {
        try{
            socket = new Socket(InetAddress.getByName(hostName), serverPort);
            System.out.println("CLIENT: Successfully connected to: "+socket.getInetAddress().getHostName()+":"+serverPort);
        }catch(IOException e){
            System.out.println("CLIENT: Failed to connect to: "+hostName);
        }
    }
}
