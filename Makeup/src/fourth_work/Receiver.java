package fourth_work;

import java.net.DatagramPacket;
import java.net.DatagramSocket;

/**
 * Created by Amanda on 17/1/5.
 * @decription 聊天室的接收端
 */
public class Receiver implements Runnable{
    private DatagramPacket datagramPacket = null;
    private DatagramSocket datagramSocket = null;

    public Receiver(DatagramSocket datagramSocket) {
        this.datagramSocket = datagramSocket;
    }
    @Override
    public void run() {
        boolean flag = true;
        while (flag) {
            try {
                //每次读取1K
                byte[] bytes = new byte[1024];
                datagramPacket = new DatagramPacket(bytes, bytes.length);
                datagramSocket.receive(datagramPacket);

//                String str = datagramPacket.getData().toString();
//                System.out.println(str);
                byte[] bytes1 = datagramPacket.getData();
//                String str = new String(bytes1);

                //需要取得数据包长度的数据
                String str = new String(bytes1,0,datagramPacket.getLength());
                System.out.println(str);
                if (str.equals("byebye")) {
                    flag = false;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
