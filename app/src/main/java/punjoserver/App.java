package punjoserver;

public class App {
    public static void main(String[] args) {
        ServerUtil serverUtil = new ServerUtil(6667);
        serverUtil.listen();
        try {
            serverUtil.getListenThread().join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
