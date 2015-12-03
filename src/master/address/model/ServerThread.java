package master.address.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by Thanh-Phong on 11/30/2015.
 */
public class ServerThread extends Thread {

    private Socket socket = null;
    private String message = null;
    private PrintWriter out;
    private BufferedReader in;

    public ServerThread(Socket socket){
        super("ServerThread");
        //System.out.println("Created new thread...");
        this.socket = socket;
    }

    public Socket getSocket(){
        return socket;
    }

    public String getMessage(){
        return message;
    }

    public void nullOutMessage(){
        message = null;
    }

    public void setOutputMessage(String msg){
        out.println(msg);
        message = null;
    }

    public void run(){
        //System.out.println(this.getName() + " has started running.");
        try{
            in = new BufferedReader( new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(),true);

            String inputLine;
            while( (inputLine = in.readLine()) != null ){
                /*
                When this particular server thread receives a message, toss
                it onto a pallet and let the master server handle it
                 */
                message = inputLine;
            }

        }catch(IOException e){
            e.printStackTrace();
        }finally{
            try{
                in.close();
                out.close();
            }catch(Exception e){
                System.err.println(e.getMessage());
            }
        }
    }
}
