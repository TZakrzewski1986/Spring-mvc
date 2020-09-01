package com.sda.mvc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import com.sda.mvc.service.UserService;

@Controller
public class IndexController {

    @Autowired
    private UserService userService;

    @GetMapping("/index")
    public ModelAndView getIndexView() {
        return new ModelAndView("index", "user",
                userService.getNameAndSurnameLoggedUser());
    }

    @GetMapping("/login")
    public String getLoginView() {
        return "login";
    }
}
