package ca.ubc.cs.cs317.dnslookup;

public class Query{
    int queryId;
    int flags;
    int qdCount;
    int anCount;
    int nsCount;
    int arCount;
    Question question;

    public Query(String hostName, RecordType type, int queryId){
        question = new Question(hostname);

    }
}