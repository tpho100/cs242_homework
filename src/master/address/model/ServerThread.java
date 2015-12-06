package master.address.model;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Created by Thanh-Phong on 11/30/2015.
 */
public class ServerThread extends Thread {

    private Socket socket;
    private String message;
    private ArrayList<String> fileContents;
    private PrintWriter out;
    private BufferedReader in;
    private boolean incomingFile = false;

    public String getMessage(){
        return message;
    }
    public ServerThread(Socket socket){
        super("ServerThread");
        //System.out.println("Created new thread...");
        this.socket = socket;
    }
    public Socket getSocket(){
        return socket;
    }
    public ArrayList<String> getFileContents(){
        return fileContents;
    }
    public boolean getIncomingStatus(){
        return incomingFile;
    }
    public void setIncomingFile(boolean incomingFile) {
        this.incomingFile = incomingFile;
    }
    public void setOutputMessage(String msg){
        out.println(msg);
        message = null;
    }
    public void setOutputFile(ArrayList<String> fileContents){
        incomingFile = true;
        for(String line : fileContents){
            out.println(line);
        }
        fileContents.clear();
        incomingFile = false;
    }
    public void run(){
        //System.out.println(this.getName() + " has started running.");
        try{
            in = new BufferedReader( new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(),true);

            if( incomingFile == true ){
                    Thread t = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        String line;
                        try{
                            while((line = in.readLine()) != null) {
                                fileContents.add(line);
                            }
                        }catch(Exception E){

                        }

                    }
                });
            }

            String inputLine;
            while( (inputLine = in.readLine()) != null){
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
