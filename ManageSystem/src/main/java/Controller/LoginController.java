package Controller;

import Service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by DZ on 2016/4/30.
 */
@Controller
@RequestMapping(value = "/LoginController")
public class LoginController {

    @Autowired
    private LoginService loginService;

    @RequestMapping(value = "/jump")
    public String jump() {
        System.out.println("in LoginController jump");
        return "Login";
    }

    @RequestMapping(value = "/login")
    @ResponseBody
    public boolean login(String username , String password)
    {
        System.out.println("in LoginController login " + username + " " + password);
        return loginService.loginjudge(username , password);
    }
}
