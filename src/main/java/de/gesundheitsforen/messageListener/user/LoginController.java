package de.gesundheitsforen.messageListener.user;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

    @GetMapping("/")
    public String hello(){
        return "hello";
    }

    @GetMapping("/login")
    public String login(@RequestParam(name="login", required=false) String name, Model model) {
        model.addAttribute("name", name);
        return "login";
    }

}