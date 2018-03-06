package ca.ubc.cs.cs317.dnslookup;

import java.io.*;


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

    public byte[] getBytes(){
        try{
            ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
            DataOutputStream dataOut = new DataOutputStream(byteOut);

            dataOut.write(getURLBytes());
            dataOut.writeShort(qType.getCode());
            dataOut.writeShort(qClass.getCode());

            return byteOut.toByteArray();
        }catch (Exception e){
            System.err.println(e.toString());
        }

        return new byte[]{};
    }

    public byte[] getURLBytes(){
        String[] hostNameArgs = qName.split("\\.");

        try{
            ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
            DataOutputStream dataOut = new DataOutputStream(byteOut);

            for(String arg : hostNameArgs){
                dataOut.writeByte(arg.length());
                dataOut.writeBytes(arg);
            }

            dataOut.writeByte(0);

            return byteOut.toByteArray();
        }catch(IOException e){
            System.err.print(e.toString());
        }
        return new byte[]{};
    }

    public void setType(RecordType type){
            this.qType = type;
    }

    public void setClass(RecordClass cl){
        this.qClass = cl;
    }

    public String getQName(){ return qName;};

    public RecordType getQType() {
        return qType;
    }
}