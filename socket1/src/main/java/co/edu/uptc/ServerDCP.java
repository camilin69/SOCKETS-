package co.edu.uptc;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class ServerDCP {
    public static final int PORT = 1234;

    public static void main(String[] args) throws IOException {
        byte[] buffer = new byte[1024];


        System.out.println("++++++++++++++++++++++++++++SERVER INITIALIZED++++++++++++++++++++++++++++");

        DatagramSocket socketUDP = new DatagramSocket(PORT);


        DatagramPacket request = new DatagramPacket(buffer, buffer.length);

        socketUDP.receive(request);

        String msgRequest = new String(request.getData(), 0, request.getLength());

        System.out.println(msgRequest);

        int portClient = request.getPort();
        InetAddress address = request.getAddress();


        String msgResponse = "SERVER: WASSUP NIGGA";
        buffer = msgResponse.getBytes();

        DatagramPacket response = new DatagramPacket(buffer, buffer.length, address, portClient);

        socketUDP.send(response);

        socketUDP.close();





    }

}
