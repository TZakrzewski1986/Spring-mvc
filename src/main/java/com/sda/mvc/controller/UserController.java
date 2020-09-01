package com.sda.mvc.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.sda.mvc.model.dao.UserDao;
import com.sda.mvc.model.dto.UserDto;
import com.sda.mvc.model.dto.UserSecDto;
import com.sda.mvc.service.UserService;

@Controller
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/adduser")
    public ModelAndView getAddUserView() {
        return new ModelAndView("newuser", "newUser", new UserSecDto());
    }

    @GetMapping("/users")
    public String getAllUsersView(Model model) {
        List<UserDto> allUsers = userService.getAllUsers();
        model.addAttribute("allUsers", allUsers);
        return "usersview";
    }

    @PostMapping("/adduser")
    public String addNewUser(@ModelAttribute UserSecDto user) {
        System.out.println(user.getName() + " " + user.getSurname());
        userService.addNewUser(user);
        return "redirect:index";
    }

    @PostMapping("/deleteuser")
    public String deleteUser(@ModelAttribute UserDto userDto) {
        System.out.println(userDto.getName() + " " + userDto.getSurname());
        userService.deleteUser(userDto);
        return "redirect:users";
    }
}
