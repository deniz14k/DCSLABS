package lab.scd.net.chat;

import java.net.*;
import java.io.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Server {
    private static DatagramSocket socket;
    private static JTextArea chatArea;
    private static JTextField messageField;

    public static void main(String[] args) throws IOException {
        JFrame frame = new JFrame("Server Chat");
        frame.setSize(400, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        chatArea = new JTextArea();
        chatArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(chatArea);

        messageField = new JTextField(30);
        JButton sendButton = new JButton("Send");

        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());
        panel.add(messageField);
        panel.add(sendButton);

        frame.add(scrollPane, BorderLayout.CENTER);
        frame.add(panel, BorderLayout.SOUTH);
        frame.setVisible(true);

        socket = new DatagramSocket(9876); // Server listens on port 9876

        // Start the thread to receive messages
        Thread receiveThread = new Thread(() -> {
            try {
                byte[] receiveBuffer = new byte[1024];
                while (true) {
                    DatagramPacket receivePacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);
                    socket.receive(receivePacket);
                    String receivedMessage = new String(receivePacket.getData(), 0, receivePacket.getLength());
                    chatArea.append("Client: " + receivedMessage + "\n");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        receiveThread.start();

        // Action listener for send button
        sendButton.addActionListener(e -> sendMessage());

        // Also send when pressing Enter
        messageField.addActionListener(e -> sendMessage());
    }

    private static void sendMessage() {
        try {
            String message = messageField.getText();
            if (!message.isEmpty()) {
                byte[] sendBuffer = message.getBytes();
                DatagramPacket sendPacket = new DatagramPacket(sendBuffer, sendBuffer.length, InetAddress.getByName("172.20.10.6"), 9877); // Send to client at port 9877
                socket.send(sendPacket);
                chatArea.append("Server: " + message + "\n");
                messageField.setText("");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

