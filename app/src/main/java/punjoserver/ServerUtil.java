package punjoserver;

import java.net.*;

import java.util.*;

public class ServerUtil {

    private String host;
    private int port;

    private Thread listenThread;
    private ServerSocket serverSocket;
    private Map<Long, Connection> connections;

    public ServerUtil(int port){
        this.port = port;
        connections = new HashMap<>();
    }

    public void listen(){ // OperationCompletedListener listener
        listenThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    serverSocket = new ServerSocket(port);
                    System.out.println("Listening on port: " + port);
                } catch (Exception e) {
                    e.printStackTrace();
                    return;
                }
                while(true){
                    try {
                        Socket clientSocket = serverSocket.accept();
                        Connection conn = new Connection(clientSocket);
                        conn.recieve();
                        connections.put(conn.getClientID(), conn);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        listenThread.start();
    }

    public void sendMessage(Long clientID, String message, OperationCompletedListener listener) {
        connections.get(clientID).send(message);
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public Thread getListenThread() {
        return listenThread;
    }

    public void setListenThread(Thread listenThread) {
        this.listenThread = listenThread;
    }
}
