
import java.util.*;

public class playground{
    public static void main(String[] args){
        Question q = new Question("www.ugrad.cs.ubc.ca", RecordType.A);
        System.out.println(q.getHexString());
    }

    public static void convert(){
        String url = "www.ugrad.cs.ubc.ca";
        byte[] urlbyte = url.getBytes();
        
        for(byte b: urlbyte){
            System.out.printf("%02X ", b);
        }
    }
}