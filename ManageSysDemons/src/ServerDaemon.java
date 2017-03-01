import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.Buffer;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.function.BooleanSupplier;

/**
 * Created by DZ on 2016/5/7.
 */
public class ServerDaemon {
    public static final int PORT = 8888;
    //最大线程数
    public static final int MAX_THREADS = 10;
    //当前已经创建的线程
    public static int CURRENT_THREADS = 0;
    private ServerSocket serverSocket;
    public static Socket clientSocket;
    public static PrintWriter printWriter;
    private static LinkedList<String> buffer;

    public ServerDaemon() {
        System.out.println("守护程序开启");
        buffer = new LinkedList<>();
        try {
            serverSocket = new ServerSocket(PORT);
            //监听是否有链接请求
            while (true) {
                if (CURRENT_THREADS < MAX_THREADS) {
                    ServerTread thread = new ServerTread(serverSocket.accept());
                    thread.start();
                    System.out.println("收到一个连接请求");
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
            try {
                if (serverSocket != null) {
                    serverSocket.close();
                }
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }

    public synchronized static void sendMessage(String message) {


        if (message.equals("!")) {
            System.out.println("检测到断连，准备发送确认信息");

//            System.out.println("正在发送" + message);
//            printWriter.println(message);
//            printWriter.flush();

            try {
                if (printWriter != null) {
                    printWriter.close();
                    printWriter = null;
                }
                if (clientSocket != null) {
                    clientSocket.close();
                    clientSocket = null;
                }
            } catch (IOException e) {
                System.out.println("断连失败");
                e.printStackTrace();
            }
        } else {
            if (message.equals("@")) {
                if (ServerDaemon.buffer.size() != 0) {
                    System.out.println("准备向屏幕发送数据");
                    for (int i = 0; i < buffer.size(); i++) {
                        String eachMes = buffer.removeFirst();
                        System.out.println("正在发送" + eachMes);
                        printWriter.println(eachMes);
                        printWriter.flush();
                    }
                }
            } else {
                if (clientSocket == null || printWriter == null) {
                    ServerDaemon.buffer.add(message);
                    System.out.println("没有连接，已将东西存入缓存，当前缓存内容");
                    System.out.println(ServerDaemon.buffer);
                } else {
                    System.out.println("准备向屏幕发送数据");
                    if (ServerDaemon.buffer.size() == 0) {
                        System.out.println("正在发送数据" + message);
                        printWriter.println(message);
                        printWriter.flush();
                        System.out.println("数据发送完毕");
                    } else {
                        ServerDaemon.buffer.add(message);
                        for (int i = 0; i < buffer.size(); i++) {
                            String eachMes = buffer.removeFirst();
                            System.out.println("正在发送" + eachMes);
                            printWriter.println(eachMes);
                            printWriter.flush();
                        }
                    }
                }
            }
        }
    }
}

class ServerTread extends Thread {
    private Socket socket;
    BufferedReader bufferedReader;
    PrintWriter printWriter;

    public ServerTread(Socket socket) {
        try {
            this.socket = socket;
            ServerDaemon.CURRENT_THREADS++;
            bufferedReader = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
            printWriter = new PrintWriter(new OutputStreamWriter(this.socket.getOutputStream()), true);

        } catch (IOException e) {
            try {
                if (this.bufferedReader != null) {
                    this.bufferedReader.close();
                }

                if (this.printWriter != null) {
                    this.printWriter.close();
                }

                if (this.socket != null) {
                    this.socket.close();
                }
            } catch (IOException e1) {
                e1.printStackTrace();
            }

            e.printStackTrace();
        }
    }

    public void run() {
        try {
            Boolean shortLink = false;
            while (!shortLink) {
                String str = bufferedReader.readLine();
                //System.out.println("收到数据" + str);
                if (str != null) {
                    if (str.equals("@")) {
                        System.out.println("收到数据" + str);
                        System.out.println("设定为长连接，将持续监听");
                        ServerDaemon.clientSocket = this.socket;
                        ServerDaemon.printWriter = this.printWriter;
                        ServerDaemon.sendMessage(str);
                    } else {
                        System.out.println("收到数据" + str);
                        System.out.println("设定为短连接");
                        shortLink = true;
                        ServerDaemon.sendMessage(str);
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("连接出现错误");
            e.printStackTrace();
        } finally {
            try {
                if (this.bufferedReader != null) {
                    this.bufferedReader.close();
                    this.bufferedReader = null;
                }

                if (this.printWriter != null) {
                    this.printWriter.close();
                    this.printWriter = null;
                }

                if (this.socket != null) {
                    this.socket.close();
                    this.socket = null;
                }
            } catch (IOException e) {
                System.out.println("链接断开失败");
                e.printStackTrace();
            }
        }
    }
}