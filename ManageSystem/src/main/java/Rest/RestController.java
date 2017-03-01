package Rest;

import DAO.VideoDAO;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;


import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by DZ on 2016/5/1.
 */
@RequestMapping(value = "/rest")
@Controller
public class RestController {
    @Autowired
    private VideoDAO videoDAO;

    //这里是发送信息给Qt的方法，flag代表是立即播放还是排队播放，"1"代表立即播放，"0"代表排队播放
    //idList代表的是视频的id列表，那么nameList代表视频名字的列表，这个和id的索引值是一一对应的。
    public void sendToQt(String flag, List<String> idList, List<String> nameList) {
        String message = "";

        if (flag.equals("0")) {
            message = "4";
        } else {
            message = "5";
        }

        for (int i = 0; i < idList.size(); i++) {
            message = message + idList.get(i) + ":" + nameList.get(i) + "|";
        }

        Socket socket = null;
        PrintWriter writer = null;
        try {
            InetAddress address = InetAddress.getLocalHost();
            socket = new Socket(address, 8888);
            writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
            writer.println(message);
            writer.flush();
            writer.close();
            socket.close();
        } catch (UnknownHostException e) {
            System.out.println("找不到本机IP");
            e.printStackTrace();
        } catch (IOException e) {
            if (writer != null) {
                writer.close();
            }
            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException e1) {
                    System.out.println("出现错误后未能关闭Socket");
                    e1.printStackTrace();
                }
            }
            System.out.println("无法建立socket，守护程序可能没有开启");
            e.printStackTrace();
        }
    }

    //这里向客户端发送播放、暂停、清空列表三个命令，对应的值分别是“1”、“2”、“3”
    @RequestMapping(value = "/statue/{flag}")
    public String setTheStatueofQt(@PathVariable String flag) {

        //这里是命令发送逻辑
        System.out.println("准备发送指令");
        Socket socket = null;
        PrintWriter writer = null;
        try {
            InetAddress address = InetAddress.getLocalHost();
            socket = new Socket(address, 8888);
            writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
            writer.println(flag);
            writer.flush();
            writer.close();
            socket.close();
        } catch (UnknownHostException e) {
            System.out.println("找不到本机IP");
            e.printStackTrace();
        } catch (IOException e) {
            if (writer != null) {
                writer.close();
            }
            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException e1) {
                    System.out.println("出现错误后未能关闭Socket");
                    e1.printStackTrace();
                }
            }
            System.out.println("无法建立socket，守护程序可能没有开启");
            e.printStackTrace();
        }

        return "redirect:/RemoteController/jump";
    }


    //以下内容与Qt客户端无关
    //===========================================================================================================================================
    //这个方法用来接收播放列表
    @RequestMapping(value = "/send/{idString}")
    public String sendList(@PathVariable String idString) {
        //发送这个给Qt端，使用Socket发送这个字符串，这个字符串是一个列表,存的是视频的id，用字母“g”隔开
        System.out.println("发送的数据" + idString);

        //将数据整理，变成id+名字的模式
        List<String> idList = Arrays.asList(idString.split("g"));
        String flag = idList.get(0);
        ArrayList<String> tempList = new ArrayList<String>();

        for (int i = 1; i < idList.size(); i++) {
            tempList.add(idList.get(i));
        }

        idList = tempList;

        ArrayList<String> nameList = new ArrayList<String>();

        for (String id : idList) {
            nameList.add(videoDAO.getNameById(Integer.parseInt(id)));
        }
        System.out.println("即将于Qt客户端进行一次通信");
        System.out.println(flag);
        System.out.println(idList);
        System.out.println(nameList);
        sendToQt(flag, idList, nameList);
        System.out.println("通信完毕");

        return "redirect:/RemoteController/jump";
    }

    @RequestMapping("/download")
    public ResponseEntity<byte[]> download() throws IOException {
        String path = "D:\\study\\java\\ManageSystem\\target\\SEU-1.0-SNAPSHOT\\video\\part.txt";
        File file = new File(path);
        HttpHeaders headers = new HttpHeaders();
        String fileName = new String("part.txt".getBytes("UTF-8"));//为了解决中文名称乱码问题
        headers.setContentDispositionFormData("attachment", fileName);
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file),
                headers, HttpStatus.OK);
    }

    @RequestMapping("/downloadBeta/{id}")
    public void download(HttpServletResponse res, @PathVariable String id) throws IOException {
        OutputStream os = res.getOutputStream();
        try {
            String path = videoDAO.getDirById(Integer.parseInt(id));
            String name = videoDAO.getNameById(Integer.parseInt(id));
            File file = new File(path);
            res.reset();
            res.setHeader("Content-Disposition", "attachment; filename=" + name);
            res.setContentType("application/octet-stream; charset=utf-8");
            os.write(FileUtils.readFileToByteArray(file));
            os.flush();
        } finally {
            if (os != null) {
                os.close();
            }
        }
    }
}
