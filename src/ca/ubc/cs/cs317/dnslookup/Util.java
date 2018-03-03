public class Util{

    public static void main(String[] args){
        String url = "www.ugrad.cs.ubc.ca";
        translateHostNameToHex(url);

    }

    public static String translateHostNameToHex(String hostName){
        String[] hostNameArgs = hostName.split("\\.");
        StringBuilder sb = new StringBuilder();
        
        
        for(String arg : hostNameArgs){
            char[] chars = arg.toCharArray();
            sb.append(intTo2ByteHex(chars.length));
            for(char c: chars){
                sb.append(intTo2ByteHex((int)c));
            }
        }
        sb.append("00");
        System.out.println(sb.toString());
        
        return sb.toString();
    }

    public static String intTo2ByteHex (int i){
        return String.format("%02X", i);
    }

    public static String intTo4ByteHex (int i){
        return String.format("%04X", i);
    }
}

//03 77 77 77 05 75 67 72 61 64 02 63 73 03 75 62
//63 02 63 61 00

//03 77 77 77 05 75 67 72 61 64 02 63 73 03 75 62 
//63 02 63 61 00 

//03 77 77 77 05 75 67 72 61 64 02 63 73 03 75 62
//63 02 63 61 00