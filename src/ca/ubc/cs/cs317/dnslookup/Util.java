
package ca.ubc.cs.cs317.dnslookup;
import java.util.ArrayList;
import java.util.Arrays;

public class Util{

    public static void main(String[] args){
        String url = "www.ugrad.cs.ubc.ca";

        //translateHostNameToHex(url);
        String s = "037777770575677261640263730375626302636100";

        byte[] bytes = hexStringToByteArray(s);
        for(byte b: bytes){
            System.out.printf("%hhx",b);
        }


    }

    public static String translateHostNameToHex(String hostName){
        String[] hostNameArgs = hostName.split("\\.");
        StringBuilder sb = new StringBuilder();
        
        
        for(String arg : hostNameArgs){
            char[] chars = arg.toCharArray();

            sb.append(String.format("%02x", chars.length));
            for(char c: chars){
                sb.append(String.format("%02x", (int)c));
            }
        }
        sb.append("00");

        
        return sb.toString();
    }


    public static String intToByteHex (int i){
        return String.format("%02X", i);
    }

    public static String intTo2ByteHex (int i){
        return String.format("%04X", i);
    }

    public static byte[] hexStringToByteArray(String s) {
        int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                    + Character.digit(s.charAt(i+1), 16));
        }
        return data;
    }

}

//03 77 77 77 05 75 67 72 61 64 02 63 73 03 75 62
//63 02 63 61 00


