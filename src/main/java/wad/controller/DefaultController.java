package wad.controller;
 
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import wad.domain.PetSpecies;
import wad.domain.User;
import wad.repository.PetSpeciesRepository;
import wad.repository.UserRepository;
 
@Controller
public class DefaultController {
 
    @Autowired
    private PasswordEncoder passwordEncoder;
 
    @Autowired
    private UserRepository userDetailsRepository;
    
    @Autowired
    private PetSpeciesRepository petSpeciesRepository;
 
    @PostConstruct
    public void init() {
        if (userDetailsRepository.findByUsername("user") != null) {
            return;
        }
 
        User user = new User();
        user.setUsername("user");
        user.setEmail("user@gmail.com");
        user.setPassword(passwordEncoder.encode("user"));
 
        user = userDetailsRepository.save(user);
        
        User user2 = new User();
        user2.setUsername("foo");
        user2.setEmail("foobar@gmail.com");
        user2.setPassword(passwordEncoder.encode("bar"));
 
        user2 = userDetailsRepository.save(user2);
        
        PetSpecies petSpecies = new PetSpecies();
        petSpecies.setName("pupu");
        petSpecies = petSpeciesRepository.save(petSpecies);
    }
 
    @RequestMapping("*")
    public String handleDefault() {
        return "redirect:/index";
    }
}