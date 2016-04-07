import java.io.*;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import packet.AckPacket;
import packet.DataPacket;
import packet.Packet;

class ClientSide {
	static InetAddress IPAddress;
	static int port;
	static DatagramSocket clientSocket=null;
	static DatagramPacket receivePacket;
	public static void main(String args[]) throws SocketException {

		try {
			clientSocket = new DatagramSocket();
		} catch (SocketException e) {
			e.printStackTrace();
		}
		 IPAddress=null;
		try {
			IPAddress = InetAddress.getByName("192.168.1.100");//omar
//			IPAddress = InetAddress.getByName("192.168.1.101");//khaled

		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		port = 9876;
		byte[] sendData ;
		byte[] receiveData = new byte[512];
		DataPacket p=null;
		File file=null;
		FileOutputStream fop=null;
		
			String sentence = "lolo.txt";

			sendData = sentence.getBytes();

			DatagramPacket sendPacket = new DatagramPacket(sendData,
					sendData.length, IPAddress, port);
			while(true){
			try{
				System.out.println(clientSocket.getPort()+"    "+clientSocket.getLocalPort());
			clientSocket.send(sendPacket);
			
			clientSocket.setSoTimeout(5000);
			 receivePacket = new DatagramPacket(receiveData,
					receiveData.length);
		
			clientSocket.receive(receivePacket);
			port = receivePacket.getPort();
			 p=new DataPacket(receivePacket.getData());
			break;
			}catch( IOException t){
				System.out.println("TimeOut!! :( ");
			}
			}
		int seqNo = 0;
		try {
			file=new File("lolo.txt");
			fop=new FileOutputStream(file);
			
			if(!file.exists()){
				file.createNewFile();
			}
			Packet ackPack=null;
			while(p.getLength()!=0){
				if(!corrupt(p)&&hasSeq(p,seqNo)){
					fop.write(p.getData().getBytes());
					ackPack=new AckPacket((short)10, (short)8, seqNo);
					  seqNo = (seqNo == 1? 0 : 1);
					udt_send(ackPack);
				}
				else{
					udt_send(ackPack);
				}
				
				receiveData = new byte[512];
				 receivePacket = new DatagramPacket(receiveData,
							receiveData.length);
				clientSocket.receive(receivePacket);	
			   p=new DataPacket(receivePacket.getData());
				
			}	
			fop.flush();
			fop.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			try {
				fop.flush();
				fop.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}

		clientSocket.close();

	}
    static boolean corrupt(DataPacket p){
        return false;
    }
    static boolean hasSeq(DataPacket p, int seq){
    	return (p.getSeqno()==seq);
    }
    static void udt_send(Packet packet){
        try {
            DatagramPacket sendPacket =
                    new DatagramPacket(packet.getAckPacket(), 8 , IPAddress, port);
            clientSocket.send(sendPacket);
        } catch (IOException ex) {
            Logger.getLogger(ClientSide.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}