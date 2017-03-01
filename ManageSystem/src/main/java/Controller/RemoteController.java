package Controller;

import Service.AddVideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * Created by DZ on 2016/5/1.
 */
@RequestMapping(value = "/RemoteController")
@Controller
public class RemoteController {
    @Autowired
    private AddVideoService addVideoService;

    @RequestMapping(value = "/jump")
    public String jump(Model model) {
        System.out.println("in AddVideoController jump");
        List videoInfos = addVideoService.getAllVideoInfo();
        model.addAttribute("videoInfos", videoInfos);
        return "RemoteControl";
    }
}
