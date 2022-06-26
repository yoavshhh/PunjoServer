package punjoserver;

import java.io.*;
import java.net.*;

public class Connection {

    public static Long ID = 1000L;
    
    private Long clientID;

    private Socket client;
    private Thread inputThread;
    private Thread outputThread;

    public Connection(Socket socket){
        this.clientID = ID++;
        this.client = socket;
    }

    public void recieve(){
        inputThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while(true){
                    try {
                        ObjectInputStream ois = new ObjectInputStream(client.getInputStream());
                        MyMessage myMessage = (MyMessage) ois.readObject();
                        System.out.println(myMessage.getMessage());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            }
        });
        inputThread.start();
    }

    public void send(String message){
        if(outputThread != null) {
            try {
                outputThread.join();
            } catch (Exception e) {
                e.printStackTrace();
                return;
            }
        }

        outputThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    ObjectOutputStream oos = new ObjectOutputStream(client.getOutputStream());
                    MyMessage myMessage = new MyMessage(message);
                    oos.writeObject(myMessage);
                    oos.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        outputThread.start();
    }

    public Long getClientID() {
        return clientID;
    }

    public void setClientID(Long clientID) {
        this.clientID = clientID;
    }
    
}
