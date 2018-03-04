import java.io.*;
import java.net.*;

public class UDPClient {
    static final int MAX_QUERY_ID;
    private static Random random = new Random();


    public static void connect(DNSNode node, InetAddress server) throws IOException{
        
        Query query = new Query(node.getHostName(), node.getType(), random.nextInt(MAX_QUERY_ID));

        DatagramSocket clientSocket = new DatagramSocket();

        String inputFileName = "DNSInitialQuery.bin";
        String outputFileName = "example.bin";

        File inputFile = new File("/home/wen/" + inputFileName);

        try {
            BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream("/home/wen/" + outputFileName));
            BufferedInputStream in = new BufferedInputStream(new FileInputStream(inputFile));

            int n = 0;
            byte[] buf = new byte[1024];

            n = in.read(buf, 0, (int)inputFile.length());

            InetAddress address = InetAddress.getByName("198.162.35.1");

            clientSocket.setSoTimeout(5000);

            DatagramPacket packet = new DatagramPacket(buf, buf.length, address, 53);
            clientSocket.send(packet);

            DatagramPacket receivePacket = new DatagramPacket(buf, buf.length);
            clientSocket.receive(receivePacket);
            buf = receivePacket.getData();

            //System.out.println(Arrays.toString(buf));
            out.write(buf,0,receivePacket.getLength());

            in.close();
            out.close();
        } catch(SocketTimeoutException e3){
            System.err.println("Socket Timed Out Exception: " + e3.toString());
        } catch (UnknownHostException e2){
            System.err.println("Unknown Host Exception: " + e2.toString());
        } catch (IOException e1) {
            e1.printStackTrace();
        }


        System.out.println("Client: Packet Sent");

        clientSocket.close();
    }
}
