package punjoserver;

public interface ConnectionEventListener {
    void onConnectionRecieved(Connection conn);
    void onConnectionClosed(Connection conn);
    void onConnectionSentMessage(MyMessage message);
}
