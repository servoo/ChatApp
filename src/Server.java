import com.sun.org.slf4j.internal.Logger;
import sun.security.ssl.SSLSocketImpl;

import java.io.*;
import java.lang.reflect.Array;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;

public class Server {

    ServerSocket serverSocket;
    static int clientCount = 1;
    ArrayList<PrintWriter> clientOutputStreams;

    public static void main(String[] args) {
        Server server = new Server();
        server.go();
    }

    private void go() {
        try {
            clientOutputStreams = new ArrayList<>();
            serverSocket = new ServerSocket(4242);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client #" + clientCount++ + " connected");

                PrintWriter writer = new PrintWriter(clientSocket.getOutputStream());
                clientOutputStreams.add(writer);

                Thread t = new Thread(new ClientHandler(clientSocket));
                t.start();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private class ClientHandler implements Runnable {
        BufferedReader reader;
        Socket socket;

        public ClientHandler(Socket clientSocket) {
            socket = clientSocket;
            try {
                InputStreamReader streamReader = new InputStreamReader(socket.getInputStream());
                reader = new BufferedReader(streamReader);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        @Override
        public void run() {
            String message;
            try {
                while ((message = reader.readLine()) != null) {
                    sendToAllClients(message);
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    private void sendToAllClients(String message) {
        Iterator it = clientOutputStreams.iterator();
        while (it.hasNext()) {
            PrintWriter writer = (PrintWriter) it.next();
            writer.println(message);
            writer.flush();
        }
    }
}
