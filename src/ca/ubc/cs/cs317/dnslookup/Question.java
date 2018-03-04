package ca.ubc.cs.cs317.dnslookup;
public class Question{
    private String qName;
    private RecordType qType = RecordType.A;
    private RecordClass qClass = RecordClass.IN;

    
    public Question(String hostName){

        this.qName = hostName;
    }

    public Question(String hostName, RecordType type){
        this(hostName);
        this.qType = type;
    }

    public String getHostNameHex(){

        return Util.translateHostNameToHex(qName);
    }

    public String getHexString(){
        return getHostNameHex() + Util.intTo2ByteHex(qType.getCode()) + Util.intTo2ByteHex(qClass.getCode());
    }

}