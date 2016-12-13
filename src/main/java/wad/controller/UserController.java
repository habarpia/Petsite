package wad.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import wad.domain.User;
import wad.repository.UserRepository;

@Controller
public class UserController {
    @Autowired
    private UserRepository userRepository;
    
    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public String list(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "users";
    }
    
    @RequestMapping("users/{id}")
    public String getOne(Model model, @PathVariable String id) {
        if(userRepository.getOne(Long.valueOf(id)) == null) {
            return "users";
        }

        model.addAttribute("user", userRepository.getOne(Long.valueOf(id)));
        return "user";
    }

    @RequestMapping(value = "/users", method = RequestMethod.POST)
    public String create(@ModelAttribute User user) {
        userRepository.save(user);
        return "redirect:/users";
    }    
}
