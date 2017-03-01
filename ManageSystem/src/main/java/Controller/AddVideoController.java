package Controller;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.List;

import Service.AddVideoService;

/**
 * Created by DZ on 2016/4/30.
 */
@Controller
@RequestMapping(value = "/AddVideoController")
public class AddVideoController {
    @Autowired
    private AddVideoService addVideoService;

    @RequestMapping(value = "/jump")
    public String jump(Model model) {
        System.out.println("in AddVideoController jump");
        List videoInfos = addVideoService.getAllVideoInfo();
        model.addAttribute("videoInfos", videoInfos);
        return "AddVideo";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public String updateVideo(@RequestParam("files[]") MultipartFile[] files, HttpServletRequest request) throws IOException {
        System.out.println("in AddVideoController update");
        String realPath = request.getSession().getServletContext().getRealPath("/video");
        addVideoService.addVideo(files, realPath);
        return "";
    }

    @RequestMapping(value = "/delete/{id}")
    public String delete(@PathVariable Integer id) {
        System.out.println("in AddVideoController delete " + id);
        addVideoService.deleteVideo(id);
        return "redirect:/AddVideoController/jump";
    }
}
