package ca.ubc.cs.cs317.dnslookup;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.*;
import java.util.Arrays;

public class UDPConnection {

    private static final int DEFAULT_DNS_PORT = 53;

    public static DNSResponse connect(DNSQuery query, InetAddress address){


        try {
            DatagramSocket clientSocket = new DatagramSocket();

            byte[] buf = new byte[1024];
            byte[] recvBuf = new byte[1024];

            buf = query.getBytes();

            clientSocket.setSoTimeout(5000);

            DatagramPacket packet = new DatagramPacket(buf, buf.length, address, DEFAULT_DNS_PORT);
            clientSocket.send(packet);

            DatagramPacket receivePacket = new DatagramPacket(recvBuf, recvBuf.length);
            clientSocket.receive(receivePacket);

            recvBuf = receivePacket.getData();

            ByteArrayOutputStream byteOutput = new ByteArrayOutputStream(receivePacket.getLength());
            byteOutput.write(recvBuf, 0, receivePacket.getLength());

            DNSResponse response = new DNSResponse(byteOutput.toByteArray());

            clientSocket.close();

            return response;
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (UnknownHostException e){
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        }


        return null;
    }
}


