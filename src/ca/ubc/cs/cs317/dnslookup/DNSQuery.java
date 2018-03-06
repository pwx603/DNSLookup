package ca.ubc.cs.cs317.dnslookup;

import java.io.*;

public class DNSQuery{
    protected int queryId;
    protected int qr;
    protected int opCode;
    protected int aa;
    protected int tc;
    protected int rd;
    protected int ra;
    protected int z;
    protected int rcode;
    protected int qdCount;
    protected int anCount;
    protected int nsCount;
    protected int arCount;
    protected Question question;

    protected DNSQuery(){
    }

    public DNSQuery(String hostName, RecordType type, int queryId){
        question = new Question(hostName, type);
        this.queryId = queryId;
        qdCount = 1;
    }

    private int getFlag(){
        return (qr << 15) ^ (opCode << 11) ^ (aa << 10) ^ (tc << 9) ^ (rd << 8) 
                ^ (ra << 7) ^ (z << 4) ^ (rcode);

    }

    public byte[] getBytes(){
        try{
            ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
            DataOutputStream dataOut = new DataOutputStream(byteOut);
    
            dataOut.writeShort(queryId);
            dataOut.writeShort(getFlag());
            dataOut.writeShort(qdCount);
            dataOut.writeShort(anCount);
            dataOut.writeShort(nsCount);
            dataOut.writeShort(arCount);
            dataOut.write(question.getBytes());

            return byteOut.toByteArray();
        }catch(Exception e){
            e.printStackTrace();
        }
        return  new byte[]{};
    }

    public int getQueryId() {
        return queryId;
    }

    public String getQName(){
        return question.getQName();
    }

    public RecordType getQType(){
        return question.getQType();
    }
}