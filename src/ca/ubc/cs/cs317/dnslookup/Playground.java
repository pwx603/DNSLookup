package ca.ubc.cs.cs317.dnslookup;

public class Playground{


    public static void main(String[] args){
        DNSQuery q = new DNSQuery("www.cs.ubc.ca", RecordType.A, 0x2b2b);
        q.getBytes();

        Question qu = new Question("www.cs.ubc.ca");
        qu.getBytes();
    }
}