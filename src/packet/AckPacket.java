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
public class AckPacket extends Packet {
    private int ackNum;
    
    public AckPacket(short checkSum,short length,int ackNum) {
        this.checksum = checkSum;
        this.length = length;
        this.ackNum = ackNum;
    }
    
    public AckPacket(byte AckPacket[]){
        byte ackNumb[] = new byte[4];
        ackNumb[0] = AckPacket[0];
        ackNumb[1] = AckPacket[1];
        ackNumb[2] = AckPacket[2];
        ackNumb[3] = AckPacket[3];
        byte checksumb[] = new byte[2];
        checksumb[0] = AckPacket[4];
        checksumb[1] = AckPacket[5];
        byte lengthb[] = new byte[2];
        lengthb[0] = AckPacket[6];
        lengthb[1] = AckPacket[7];
        this.ackNum = ByteBuffer.wrap(ackNumb).getInt();
        this.checksum = ByteBuffer.wrap(checksumb).getShort();
        this.length = ByteBuffer.wrap(lengthb).getShort();
    }

    public int getAckNum() {
        return ackNum;
    }

    public void setAckNum(int ackNum) {
        this.ackNum = ackNum;
    }
    
    @Override
    public byte[] getAckPacket() {
        byte[] b = new byte[8];
        
        System.arraycopy(integerToBytes(ackNum, 4), 0, b, 0, 4);
        System.arraycopy(shortToBytes(checksum, 2), 0, b, 4, 2);
        System.arraycopy(shortToBytes(length, 2), 0, b, 6, 2);


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
    
//    byte[] fillBytes (int length, String s){
//        byte [] b = new byte [length];
//        
//        for(int i = 0 ; i < length - s.length() ; i++){
//            b[i] = 48;
//        }
//        System.arraycopy(s.getBytes(), 0, b , length-s.length(), s.length());
//       
//        return b;
//    }
    
    
}
