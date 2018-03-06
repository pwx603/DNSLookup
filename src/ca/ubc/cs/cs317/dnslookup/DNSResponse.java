package ca.ubc.cs.cs317.dnslookup;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.util.LinkedList;
import java.util.Set;

public class DNSResponse extends DNSQuery{
    private LinkedList<ResourceRecord> answers = new LinkedList<>();
    private LinkedList<ResourceRecord> nameServers = new LinkedList<>();
    private LinkedList<ResourceRecord> additionalRecords = new LinkedList<>();
    private byte[] byteArray;

    public DNSResponse(byte[] buf){
        try{
            this.byteArray = buf;
            ByteArrayInputStream byteInput = new ByteArrayInputStream(byteArray);
            DataInputStream dataInput = new DataInputStream(byteInput);

            this.queryId = dataInput.readUnsignedShort();
            int flag = dataInput.readUnsignedShort();
            setFlag(flag);

            this.qdCount = dataInput.readUnsignedShort();
            this.anCount = dataInput.readUnsignedShort();
            this.nsCount = dataInput.readUnsignedShort();
            this.arCount = dataInput.readUnsignedShort();

            this.question = new Question(getURL(dataInput));
            question.setType(RecordType.getByCode(dataInput.readShort()));
            question.setClass(RecordClass.getByCode(dataInput.readShort()));
            
            // Tempoary value for counting down the number of resource record needed to be filled in
            // in each category
            int anCountDown = this.anCount;
            int nsCountDown = this.nsCount;
            int arCountDown = this.arCount;

            while((dataInput.available() != 0) && dataInput.readUnsignedByte() == 0xc0){
                String name = getURL(dataInput.readUnsignedByte());
                RecordType type = RecordType.getByCode(dataInput.readShort());

                //System.out.println("The type is: " + type.getCode());

                RecordClass cl = RecordClass.getByCode(dataInput.readShort());
                long ttl = dataInput.readInt();
                int dataLen = dataInput.readUnsignedShort();
                String textResult = null;
                InetAddress inetResult = null;

                switch (type) {
                    case NS:
                        textResult = getURL(dataInput);
                        break;
                    case SOA:
                        textResult = "----";
                        break;
                    case CNAME:
                        textResult = getURL(dataInput);
                        break;
                    default:
                        inetResult = InetAddress.getByName(getResult(dataInput, dataLen));
                        break;
                }


                if(anCountDown != 0){
                    if(type == RecordType.CNAME){
                        answers.add(new ResourceRecord(name,type,ttl, textResult));
                    }else{
                        answers.add(new ResourceRecord(name,type,ttl, inetResult));}
                    anCountDown--;
                    continue;
                }

                if( nsCountDown != 0){
                    nameServers.add(new ResourceRecord(name,type,ttl, textResult));
                    nsCountDown--;
                    continue;
                }

                if(arCountDown != 0){
                    if(type == RecordType.CNAME){
                        additionalRecords.add(new ResourceRecord(name,type,ttl, textResult));
                    }else{
                        additionalRecords.add(new ResourceRecord(name,type,ttl, inetResult));}
                    arCountDown--;
                    continue;
                }

            }
            
        }catch(IOException e){
            System.err.println("Byte cannot be read.");
        }
    }

    private void setFlag(int flag){
        this.qr = flag >>> 15;
        this.opCode = flag >>> 11 & 0b1111;
        this.aa = flag >>> 10 & 0b1;
        this.tc = flag >>> 9 & 0b1;
        this.rd = flag >>> 8 & 0b1;
        this.ra = flag >>> 7 & 0b1;
        this.z = flag >>> 4 & 0b111;
        this.rcode = flag & 0b1111;
    }

    private String getURL(int index){
        StringBuilder sb = new StringBuilder();

        int charsLen;

        while((charsLen = byteArray[index++]) != 0){
            if((charsLen & 0xFF) == 0xc0){
                sb.append(getURL(byteArray[index]));
                return sb.toString();
            }else{
                for(int i = 0; i < charsLen; i++){
                    sb.append((char)byteArray[index++]);
                }
                if(byteArray[index] == 0)
                    break;
                sb.append('.');
            }   
        }
        return sb.toString();
    }

    private String getURL(DataInputStream dataInput) throws IOException{
        StringBuilder sb = new StringBuilder();
        int charsLen = dataInput.readByte();

        while(charsLen!= 0){
            if((charsLen & 0xFF) == 0xc0){
                sb.append(getURL(dataInput.readUnsignedByte()));
                return sb.toString();
            }else{
                for(int i = 0; i < charsLen; i++){
                    sb.append((char)dataInput.readByte());
                }
                if((charsLen = dataInput.readByte()) == 0)
                    break;
                sb.append('.');
            }
        }
        return sb.toString();
    }

    private String getResult( DataInputStream dataInput, int dataLen) throws IOException{
        StringBuilder sb = new StringBuilder();
        if(dataLen == 16){
            sb.append(Integer.toHexString(dataInput.readUnsignedShort()));
            for(int i = 0; i < (dataLen - 2) / 2; i++){
                sb.append(':');
                sb.append(Integer.toHexString(dataInput.readUnsignedShort()));
            }
        }else if(dataLen == 2){
            if(dataInput.readUnsignedByte() == 0xc0){
                sb.append(getURL(dataInput.readUnsignedByte()));
            }
        }else{
            sb.append(dataInput.readUnsignedByte());
            for(int i = 0; i < dataLen -1; i++){
                sb.append('.');
                sb.append(dataInput.readUnsignedByte());
            }

        }

        return sb.toString();
    }


    @Override
    public int getQueryId() {
        return super.getQueryId();
    }

    public boolean getAuthoritative(){
        return aa == 1;
    }

    public int getAnCount(){
        return anCount;
    }

    public int getNsCount(){
        return nsCount;
    }

    public int getArCount(){
        return arCount;
    }

    public LinkedList<ResourceRecord> getAnswers(){
        return answers;
    }

    public LinkedList<ResourceRecord> getNameServers(){
        return nameServers;
    }

    public LinkedList<ResourceRecord> getAdditionalRecords(){
        return additionalRecords;
    }
}

