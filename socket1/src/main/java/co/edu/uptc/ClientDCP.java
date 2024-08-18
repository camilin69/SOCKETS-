package co.edu.uptc;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class ClientDCP {
    public static void main(String[] args) throws IOException {
        byte[] buffer = new byte[1024];


        DatagramSocket socket = new DatagramSocket();
        InetAddress addressServer = InetAddress.getLocalHost();

        String msgRequest = "CLIENT: WHATS CRACKING NIGGA?";

        buffer = msgRequest.getBytes();

        DatagramPacket response = new DatagramPacket(buffer, buffer.length, addressServer, ServerDCP.PORT);
        socket.send(response);

        socket.receive(response);

        String msgResponse = new String(response.getData(), 0, response.getLength());
        System.out.println(msgResponse);

        socket.close();

    }
}
