package ca.ubc.cs.cs317.dnslookup;

public class Question{
    private String qName;
    private RecordType qType = RecordType.A;
    private RecordClass qClass = RecordClass.IN;

    
    public Question(String hostName){
        this.qName = hostname;
    }

    public Question(String hostName, RecordType type){
        this.qName = hostName;
        this.qType = type;
    }

    public String getHostNameHex(){
        return Util.translateHostNameToHex(qName);
    }

    public String getHexString(){
        return getHostNameHex() + Util.intTo4ByteHex(qType.getCode()) + Util.intTo4ByteHex(qClass.getCode());
    }

}