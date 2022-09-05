package com.kimjaejun.mytodo.controller;

import com.kimjaejun.mytodo.SessionConst;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.security.Principal;

@Controller
public class HomeController {
    @GetMapping("/")
    public String home(HttpServletRequest request){
        HttpSession session = request.getSession();
        if (session == null||session.getAttribute(SessionConst.LOGIN_MEMBER)==null) {
            return "home";
        }
        return "loginhome";
    }

    @GetMapping("/statistics")
    public String statistics(){
        return "statistics";
    }
    @GetMapping("/timeplan")
    public String timeplan(){
        return "timeplan";
    }
    @GetMapping("/group")
    public String group(){
        return "group";
    }
}
