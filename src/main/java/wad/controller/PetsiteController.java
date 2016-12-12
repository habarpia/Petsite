package wad.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PetsiteController {
    @RequestMapping("/")
    public String home() {
        return "index";
    }
}
