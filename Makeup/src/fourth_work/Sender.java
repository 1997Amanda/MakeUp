package fourth_work;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 * Created by Amanda on 17/1/5.
 * @description 聊天室的发送方
 */
public class Sender implements Runnable{
    private DatagramSocket datagramSocket = null;
    private DatagramPacket datagramPacket = null;
    private String hostName;    //主机名
    private int port;           //端口号

    public Sender (String hostName,int port,DatagramSocket datagramSocket) {
        this.hostName = hostName;
        this.port = port;
        this.datagramSocket = datagramSocket;
    }
    @Override
    public void run() {
        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(new InputStreamReader(System.in));
            String s = "";
            while ((s = bufferedReader.readLine()) != null) {
//                byte[] bytes = new byte[1024];

                //读取输入的数据，打包成数据包，设置好ip和端口号
                byte[] bytes = s.getBytes();
                datagramPacket = new DatagramPacket(bytes,bytes.length,InetAddress.getByName(hostName),port);
                datagramSocket.send(datagramPacket);

                //输入byebye时，会话结束
                if (s.equals("byebye")) {
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                bufferedReader.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
