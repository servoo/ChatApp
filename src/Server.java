import java.io.IOException;
import java.net.ServerSocket;

public class Server {

    ServerSocket serverSocket;
    static int clientCount = 1;

    public static void main(String[] args) {
        Server server = new Server();
        server.go();
    }

    private void go() {
        try {
            serverSocket = new ServerSocket(4242);

            while (true) {
                serverSocket.accept();
                System.out.println("Client #" + clientCount++ +  " connected");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
