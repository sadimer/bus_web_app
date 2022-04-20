package app.controllers;

import DAO.*;
import com.google.common.collect.Lists;
import entities.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;

@Controller
public class WebController {
    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public ModelAndView home() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("home.html");
        return modelAndView;
    }
    @RequestMapping(value = "/signin", method = RequestMethod.GET)
    public ModelAndView signin() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("signin.html");
        return modelAndView;
    }
    @RequestMapping(value = "/signin", method = RequestMethod.POST)
    public ModelAndView login(@RequestParam(name = "user_login", required = true) String user_login,
                              @RequestParam(name = "user_psw", required = true) String user_psw,
                              @RequestParam(name = "error", required = false) String error) {
        UsersDAO users = new UsersDAO();
        List<Users> result = users.filter(Map.of("loginFilter",
                Lists.newArrayList(user_login)), Users.class);
        ModelAndView modelAndView = new ModelAndView();
        if (result.size() == 0) {
            modelAndView.setViewName("signin.html");
            modelAndView.addObject("error", "User not found!");
            return modelAndView;
        } else if (result.size() > 1) {
            modelAndView.setViewName("signin.html");
            modelAndView.addObject("error", "Multiple logins found!");
            return modelAndView;
        }
        if (!result.get(0).getPasswd().equals(user_psw)) {
            modelAndView.setViewName("signin.html");
            modelAndView.addObject("error", "Wrong password!");
            return modelAndView;
        }
        modelAndView.setViewName("signin.html");
        modelAndView.addObject("user_login", user_login);
        return modelAndView;
    }
}
