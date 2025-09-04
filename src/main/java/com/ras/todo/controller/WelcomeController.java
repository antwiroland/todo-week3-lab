// package com.example.beanstalkapp.controller;

// import org.springframework.stereotype.Controller;
// import org.springframework.ui.Model;
// import org.springframework.web.bind.annotation.GetMapping;

// @Controller
// public class WelcomeController {

//     @GetMapping("/")
//     public String welcome(Model model) {
//         model.addAttribute("message", "Hello Welcome Beanstalk App");
//         model.addAttribute("title", "Beanstalk Application");
//         return "welcome";
//     }
    
//     @GetMapping("/health")
//     public String healthCheck() {
//         return "OK";
//     }
// }