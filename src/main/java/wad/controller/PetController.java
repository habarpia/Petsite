package wad.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import wad.domain.Pet;
import wad.repository.PetRepository;

@Controller
public class PetController {
    @Autowired
    private PetRepository petRepository;
    
    @RequestMapping(value = "/pets", method = RequestMethod.GET)
    public String list(Model model) {
        model.addAttribute("pets", petRepository.findAll());
        return "pets";
    }

    @RequestMapping(value = "/pets", method = RequestMethod.POST)
    public String create(@ModelAttribute Pet pet) {
        petRepository.save(pet);
        return "redirect:/pets";
    }
    
}
