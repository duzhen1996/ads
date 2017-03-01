//package Rest;
//
//import org.springframework.stereotype.Controller;
//
//import java.io.*;
//import java.net.ServerSocket;
//import java.net.Socket;
//
///**
// * Created by DZ on 2016/5/5.
// */
//public class SocketController {
//    private ServerTread serverTread;
////    private Thread thread;
//
//    public SocketController() throws IOException {
//        System.out.println("开始执行SocketController初始化操作");
//        ServerSocket server = new ServerSocket(8888);
//        while (true) {
//            serverTread = new ServerTread(server.accept());
//            Thread thread = new Thread(serverTread);
//        }
//    }
//
//    public void sendMessage(String message) {
//        serverTread.commend = 1;
//        serverTread.message = message;
//        while (serverTread.commend != 0) ;
//    }
//}
//
//class ServerTread implements Runnable {
//
//    private Socket socket;
//    private BufferedReader in;
//    private PrintWriter out;
//    public Integer commend;
//    public String message;
//
//    public ServerTread(Socket socket) throws IOException {
//        this.socket = socket;
//        in = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
//        out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(this.socket.getOutputStream())), true);//加了true会自动刷新输出缓冲区
//        commend = 0;
//    }
//
//    public void run() {
//        while (commend != 0) {
//            System.out.println("收到发送指令");
//
//            commend = 0;
//        }
//    }
//}
