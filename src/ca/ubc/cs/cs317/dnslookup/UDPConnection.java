package ca.ubc.cs.cs317.dnslookup;
import javax.management.Query;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.*;
import java.util.Arrays;

public class UDPConnection {
    static String rootServer = "198.162.35.1";

    public static void main(String[] args){
        DNSNode node = new DNSNode("www.ugrad.cs.ubc.ca", RecordType.A);
        int queryId = (int)(Math.random() * 6000);

        DNSQuery query = new DNSQuery(node.getHostName(), node.getType(), queryId);

        System.out.print("Hello, World");





        try {
            DatagramSocket clientSocket = new DatagramSocket();

            BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream("/home/wen/output.bin"));

            byte[] buf = new byte[1024];
            byte[] recvBuf = new byte[1024];


            System.out.println(query.getHexString());
            buf = Util.hexStringToByteArray(query.getHexString());

            InetAddress address = InetAddress.getByName(rootServer);

            clientSocket.setSoTimeout(5000);

            DatagramPacket packet = new DatagramPacket(buf, buf.length, address, 53);
            clientSocket.send(packet);

            DatagramPacket receivePacket = new DatagramPacket(buf, buf.length);
            clientSocket.receive(receivePacket);
            recvBuf = receivePacket.getData();

            System.out.println(recvBuf.length);
            System.out.println(Arrays.toString(buf));

            out.write(recvBuf, 0, receivePacket.getLength());

            out.close();
            clientSocket.close();
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (UnknownHostException e){
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        }



    }
}


