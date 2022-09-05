import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class SimpleClient {

    JTextField outGoing;
    PrintWriter writer;
    Socket socket;

    public void go() {
        JFrame frame = setupGUI();
        setupNetworking();
        frame.setSize(400, 500);
        frame.setVisible(true);
    }

    private JFrame setupGUI() {
        JFrame frame = new JFrame("Simple Chat Client");
        JPanel mainPanel = new JPanel();
        outGoing = new JTextField(20);
        JButton sendButton = new JButton("Send");
        sendButton.addActionListener(new SendButtonListener());

        mainPanel.add(outGoing);
        mainPanel.add(sendButton);

        frame.getContentPane().add(BorderLayout.CENTER, mainPanel);
        return frame;
    }

    private void setupNetworking() {
        try {
            socket = new Socket("127.0.0.1", 4242);
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
}
