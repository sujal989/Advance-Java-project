package virinchi.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

    @GetMapping("/")
    public String home() {
        return "forward:/home.html";
    }

    @GetMapping("/products")
    public String products() {
        return "forward:/products.html";
    }

    @GetMapping("/about")
    public String about() {
        return "forward:/about.html";
    }

    @GetMapping("/contact")
    public String contact() {
        return "forward:/contact.html";
    }

    @GetMapping("/login")
    public String login() {
        return "forward:/login.html";
    }
}