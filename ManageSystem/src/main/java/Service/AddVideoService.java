package Service;

import DAO.VideoDAO;
import Model.Video;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.SystemEnvironmentPropertySource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by DZ on 2016/5/1.
 */
@Service
public class AddVideoService {
    @Autowired
    private VideoDAO videoDAO;

    public String addVideo(MultipartFile[] files, String realPath) throws IOException {
        for (MultipartFile file : files) {
            if (file.isEmpty()) {
                System.out.println("文件未上传");
            } else {
                System.out.println("文件名称: " + file.getName());
                System.out.println("文件原名: " + file.getOriginalFilename());
                System.out.println("========================================");

                //将路径和文件名写入数据库
                if (videoDAO.getCountofVideoInfo("WHERE name = \'" + file.getOriginalFilename() + "\'").equals(0)) {
                    videoDAO.insertVideoInfo(realPath + System.getProperties().getProperty("file.separator") + file.getOriginalFilename(), file.getOriginalFilename());
                }

                //如果用的是Tomcat服务器，则文件会上传到\\%TOMCAT_HOME%\\webapps\\YourWebProject\\video中
                System.out.println("获取路径 " + realPath);
                //从这里开始文件才准备写入内存
                //这里不必处理IO流关闭的问题，因为FileUtils.copyInputStreamToFile()方法内部会自动把用到的IO流关掉，input流在这里有点奇怪，但是我觉得这个的意思是
                //webapp把文件的input流给tomcat了，tomcat来执行的input
                FileUtils.copyInputStreamToFile(file.getInputStream(), new File(realPath, file.getOriginalFilename()));
            }
        }
        System.out.println("文件已经写入");
        return "[1,2]";
    }

    public ArrayList<Video> getAllVideoInfo() {
        if (videoDAO.getCountofVideoInfo("").equals(0)) {
            return new ArrayList<Video>(0);
        }
        System.out.println(videoDAO.getAllVideoInfo());
        return (ArrayList<Video>) videoDAO.getAllVideoInfo();
    }

    public void deleteVideo(Integer id) {
        String path = "";
        if (videoDAO.getCountofVideoInfo("WHERE id = " + id).equals(0)) {
            System.out.println("数据库中并没有文件 " + id + " 的记录");
        } else {
            //把路径找出来
            path = videoDAO.getDirById(id);
            videoDAO.deleteVideoInfo("WHERE id = " + id);
            //在文件系统中真正删除这个文件
            File file = new File(path);
            if (file.exists()) {
                System.out.println("准备删除删除文件 " + path);
                // 判断是否为文件
                if (file.isFile()) {  // 为文件时调用删除文件方法
                    file.delete();
                } else {
                    System.out.println("这是一个目录而不是一个文件");
                }
            } else {
                System.out.println("这个文件不存在");
            }
        }
    }
}
