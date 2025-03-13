package com.tms.controller;

import com.tms.model.Role;
import com.tms.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ThymController {
    
    @GetMapping("/")
    public String index(Model model) {
     //   model.addAttribute("message", "Thym Controller message!");
        List<String> message = new ArrayList<>();
        message.add("Hello World 1");
        message.add("Hello World 2");
        message.add("Hello World 3");
        model.addAttribute("message", message);
        model.addAttribute("boolValue", false);
        model.addAttribute("role", Role.USER);

        User user = new User();
        user.setId(100L);
        user.setFirstname("Akakiy");
        user.setAge(70);
        model.addAttribute("user", user);
        return "index-thym";
    }
}
