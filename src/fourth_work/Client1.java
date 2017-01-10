package fourth_work;

import java.net.DatagramSocket;
import java.util.Scanner;

/**
 * Created by Amanda on 17/1/5.
 * @description 聊天室   测试客户 1号
 */
public class Client1 {
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入对方的ip地址:");
        String hostName = scanner.next();
        System.out.println("请输入对方的端口号:");
        int port = scanner.nextInt();

        System.out.println("请设置接收消息的端口号:");
        int receivePort = scanner.nextInt();

        //数据包
        DatagramSocket sendSocket = new DatagramSocket();
        DatagramSocket receiveSocket = new DatagramSocket(receivePort);

        //发送消息线程，设置好ip和端口号
        Sender sender = new Sender(hostName,port,sendSocket);
        //接受消息线程，设置好端口号
        Receiver receiver = new Receiver(receiveSocket);

        new Thread(sender).start();
        new Thread(receiver).start();
    }
}
