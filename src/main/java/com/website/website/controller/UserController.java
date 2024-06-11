package com.website.website.controller;

import com.website.website.dto.UserDto;
import com.website.website.entity.User;
import com.website.website.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;

@Controller
public class UserController {

//    @Autowired
//    UserDetailsService userDetailsService;
//
//    @Autowired
//    private UserService userService;
//
//    @GetMapping("/registration")
//    public String getRegistrationPage(@ModelAttribute("user") UserDto userDto, Model model) {
//        return "register";
//    }
//
//    @PostMapping("/registration")
//    public String saveUser(@ModelAttribute("user") UserDto userDto, Model model) {
//        userService.save(userDto);
//        model.addAttribute("message", "Registered Successfully!");
//        return "register";
//    }
//
//    @GetMapping("/login")
//    public String login() {
//        return "login";
//    }
//
//    @GetMapping("user-page")
//    public String userPage (Model model, Principal principal) {
//        UserDetails userDetails = userDetailsService.loadUserByUsername(principal.getName());
//        model.addAttribute("user", userDetails);
//        return "user";
//    }
//
//    @GetMapping("admin-page")
//    public String adminPage (Model model, Principal principal) {
//        UserDetails userDetails = userDetailsService.loadUserByUsername(principal.getName());
//        model.addAttribute("user", userDetails);
//        return "admin";
//    }

    @Autowired
    private UserDetailsService userDetailsService;

    private UserService userService;

    public UserController(UserService userService) {

        this.userService = userService;
    }

    @GetMapping("/home")
    public String home(Model model, Principal principal) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(principal.getName());
        model.addAttribute("userdetail" , userDetails);
        return "home";
    }

    @GetMapping("/login")
    public String login(Model model, UserDto userDto) {
        model.addAttribute("user", userDto);
        return "login";
    }


    @GetMapping("/register")
    public String register(Model model, UserDto userDto) {

        model.addAttribute("user", userDto);
        return "register";
    }

    @PostMapping("/register")
    public String registerSave(@ModelAttribute("user") UserDto userDto, Model model) {
        User user = userService.findByUsername(userDto.getUsername());
        if (user != null) {
            model.addAttribute("userexist", user);
            return "register";

        }
        userService.save(userDto);
        return "redirect:/register?success";
    }


    @GetMapping("/quiz")
    public String quiz() {
        return "quiz";
    }

    @GetMapping("/index")
    public String index() {
        return "index";
    }

}
