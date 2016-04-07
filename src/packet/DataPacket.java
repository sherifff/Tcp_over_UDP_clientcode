/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package packet;

import java.nio.ByteBuffer;

/**
 *
 * @author OmarElfarouk
 */
public class DataPacket extends Packet {

    private int seqNum;
    private String data;

    public DataPacket(short checkSum, short length, int seqNum, String data) {
        this.checksum = checkSum;
        this.length = length;
        this.seqNum = seqNum;
        this.data = data;
    }

    public DataPacket(byte DataPacket[]) {
        String packet = new String(DataPacket);
        byte seqNumb[] = new byte[4];
        seqNumb[0] = DataPacket[0];
        seqNumb[1] = DataPacket[1];
        seqNumb[2] = DataPacket[2];
        seqNumb[3] = DataPacket[3];
        byte checksumb[] = new byte[2];
        checksumb[0] = DataPacket[4];
        checksumb[1] = DataPacket[5];
        byte lengthb[] = new byte[2];
        lengthb[0] = DataPacket[6];
        lengthb[1] = DataPacket[7];
        this.seqNum = ByteBuffer.wrap(seqNumb).getInt();
        this.checksum = ByteBuffer.wrap(checksumb).getShort();
        this.length = ByteBuffer.wrap(lengthb).getShort();
        this.data = packet.substring(8, 8 + length);

    }

    public int getSeqno() {
        return seqNum;
    }

    public void setSeqno(int seqno) {
        this.seqNum = seqno;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @Override
    public byte[] getDataPacket() {
        byte[] b = new byte[512];

        System.arraycopy(integerToBytes(seqNum, 4), 0, b, 0, 4);
        System.arraycopy(shortToBytes(checksum, 2), 0, b, 4, 2);
        System.arraycopy(shortToBytes(length, 2), 0, b, 6, 2);
        System.arraycopy(data.getBytes(), 0, b, 8, length);

        return b;
    }

    byte[] integerToBytes(int number, int numOfBytes) {
        byte[] b = new byte[numOfBytes];
        ByteBuffer dbuf = ByteBuffer.allocate(numOfBytes);
        dbuf.putInt(number);
        b = dbuf.array();
        return b;
    }
    byte[] shortToBytes(short number, int numOfBytes) {
        byte[] b = new byte[numOfBytes];
        ByteBuffer dbuf = ByteBuffer.allocate(numOfBytes);
        dbuf.putShort(number);
        b = dbuf.array();
        return b;
    }

    byte[] fillBytes(int length, String s) {
        byte[] b = new byte[length];

        for (int i = 0; i < length - s.length(); i++) {
            b[i] = 48;
        }
        System.arraycopy(s.getBytes(), 0, b, length - s.length(), s.length());

        return b;
    }
}
