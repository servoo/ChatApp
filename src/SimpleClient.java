import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class SimpleClient {

    JTextField outGoing;
    JTextArea incoming;
    BufferedReader reader;
    PrintWriter writer;
    Socket socket;

    public void go() {
        JFrame frame = setupGUI();
        setupNetworking();

        Thread readerThread = new Thread(new IncomingReader());
        readerThread.start();

        frame.setSize(400, 500);
        frame.setVisible(true);
    }

    private JFrame setupGUI() {
        JFrame frame = new JFrame("Simple Chat Client");
        JPanel mainPanel = new JPanel();

        outGoing = new JTextField(20);
        incoming = new JTextArea(20, 20);
        incoming.setEditable(false);
        JButton sendButton = new JButton("Send");

        sendButton.addActionListener(new SendButtonListener());

        mainPanel.add(incoming);
        mainPanel.add(outGoing);
        mainPanel.add(sendButton);

        frame.getContentPane().add(BorderLayout.CENTER, mainPanel);
        return frame;
    }

    private void setupNetworking() {
        try {
            socket = new Socket("127.0.0.1", 4242);
            InputStreamReader streamReader = new InputStreamReader(socket.getInputStream());
            reader = new BufferedReader(streamReader);
            writer = new PrintWriter(socket.getOutputStream());
            System.out.println("Connection established");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private class SendButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

            try {
                writer.println(outGoing.getText());
                writer.flush();
            } catch (Exception exception) {
                exception.printStackTrace();
            }

            System.out.println("Sent: " + outGoing.getText());
            outGoing.setText("");
            outGoing.requestFocus();
        }
    }

    public static void main(String[] args) {
        SimpleClient client = new SimpleClient();
        client.go();
    }

    private class IncomingReader implements Runnable {
        @Override
        public void run() {
            String message;
            try {
                System.out.println("Reading!");
                while ((message = reader.readLine()) != null) {
                    incoming.append(message + "\n");
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}
