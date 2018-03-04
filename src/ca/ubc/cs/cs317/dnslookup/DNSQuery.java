package ca.ubc.cs.cs317.dnslookup;
public class DNSQuery {
    private int queryId;
    private int flags;
    private int qdCount;
    private int anCount;
    private int nsCount;
    private int arCount;
    private Question question;

    public DNSQuery(String hostName, RecordType type, int queryId){
        question = new Question(hostName, type);
        this.queryId = queryId;
        qdCount++;
    }

    public String getHexString(){
        StringBuilder sb = new StringBuilder();
        return sb.append(Util.intTo2ByteHex(queryId)).append(Util.intTo2ByteHex(flags))
                .append(Util.intTo2ByteHex(qdCount)).append(Util.intTo2ByteHex(anCount))
                .append(Util.intTo2ByteHex(nsCount)).append(Util.intTo2ByteHex(arCount))
                .append(question.getHexString()).toString();
    }
}