package master.address.model;
import com.sun.corba.se.spi.activation.Server;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Created by Thanh-Phong on 11/20/2015.
 */
public class ChatServer{

    public static void main(String[] args) throws IOException {

        final int portNumber = 13699; //Server port number

        List<ServerThread> clientList = new ArrayList<>(); //Track how many clients based on the # of threads
        List<String> messageQueue = new ArrayList<>(); //Thread safe list
        List< ArrayList<String> > fileQueue = new ArrayList<>(); //Thread safe file queue

        ServerSocket serverSocket = new ServerSocket(portNumber);

        Thread messageHandlerThread = new Thread(new Runnable() {

            /*
            If the worker threads have messages on them, snatch them up to the master server
            and add them to the queue.
             */

            @Override
            public void run() {
                while(true){
                    try{
                        Thread.sleep(100);
                    }catch(InterruptedException interrupt){
                        System.err.println("Message handler delay interrupted. " + interrupt.getMessage());
                    }

                    for(ServerThread client : clientList){
                        if(client.getMessage() != null){
                            messageQueue.add(client.getMessage());
                        }
                    }

                }
            }
        });

        Thread messageQueueHandlerThread = new Thread(new Runnable() {
            /*
            If there are any messages queuing, send those messages back to every
            worker thread. Those worker threads will send them to their clients.
             */
            @Override
            public void run() {
                while(true){
                    try{
                        Thread.sleep(100);
                    }catch(InterruptedException interrupt){
                        System.err.println("Message handler delay interrupted. " + interrupt.getMessage());
                    }

                    if(!messageQueue.isEmpty())
                    {
                        String msg = messageQueue.get(0);
                        messageQueue.remove(0);
                        for(ServerThread client : clientList){
                            client.setOutputMessage(msg);
                        }
                    }


                }
            }
        });

        messageHandlerThread.start();
        messageQueueHandlerThread.start();

        try{

            while (true) {
                Socket socket = serverSocket.accept();
                    System.out.println("Accepted New Client: " + socket.getPort());
                    ServerThread st = new ServerThread(socket);
                    Thread t1 = new Thread(st);
                    t1.start();
                    clientList.add(st);
                }

        } catch (IOException e) {
            System.err.println("Could not listen on port " + portNumber);
            System.exit(-1);
        }
    }
}
