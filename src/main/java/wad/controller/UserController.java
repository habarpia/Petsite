package wad.controller;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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
    
     @Autowired
    private PasswordEncoder passwordEncoder;
    
    @ModelAttribute
    private User getUser() {
        return new User();
    }
        
    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public String list(Model model){
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
        
    @RequestMapping(value = "signup",method = RequestMethod.GET)
    public String signup() {
        return "signup";
    }

    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public String create(@Valid @ModelAttribute User user, BindingResult bindingResult) {
        if(userRepository.findByUsername(user.getUsername()) != null) {
            bindingResult.rejectValue("username", "error.user", "An account already exists for this username.");
            return "signup";
        }
        if(userRepository.findByEmail(user.getEmail()) != null) {
            bindingResult.rejectValue("email", "error.email", "An account already exists for this email.");
            return "signup";
        }
        if(bindingResult.hasErrors()) {
            return "signup";
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.addAuthority("USER");
        userRepository.save(user);
        return "redirect:/login";
    }
    
    @RequestMapping(value = "login",method = RequestMethod.GET)
    public String loginView() {
        return "login";
    }
    
    @RequestMapping(value = "login",method = RequestMethod.POST)
    public String login(@Valid @ModelAttribute User user, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            return "login";
        }
        return "login";
    }
}
