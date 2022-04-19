package app.controllers;

import DAO.*;
import entities.*;
import DTO.*;
import mapper.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
@Controller
public class WebController {
    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public ModelAndView home() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("home");
        return modelAndView;
    }
    @RequestMapping(value = "/profile", method = RequestMethod.POST)
    public ModelAndView profile(@RequestParam(name = "user_id", required = true) Long user_id) {
        UsersDAO users = new UsersDAO();
        Users entity = users.getEntityById(user_id, Users.class);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("profile");
        modelAndView.addObject("user_id", user_id);
        modelAndView.addObject("is_admin", entity.getAdmin());
        modelAndView.addObject("name", entity.getName());
        modelAndView.addObject("login", entity.getLogin());
        modelAndView.addObject("email", entity.getEmail());
        modelAndView.addObject("phone", entity.getPhone());
        return modelAndView;
    }
}
