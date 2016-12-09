package wad;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

public class PetsiteController {
    @RequestMapping("*")
    @ResponseBody
    public String home() {
        return "Hello World!";
    }
}
