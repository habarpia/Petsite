package wad.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import wad.domain.PetSpecies;
import wad.repository.PetSpeciesRepository;

@Controller
public class PetSpeciesController {
    @Autowired
    PetSpeciesRepository petSpeciesRepository;
    
    @RequestMapping(value = "/petSpecies", method = RequestMethod.GET)
    public String list(Model model) {
        model.addAttribute("allPetSpecies", petSpeciesRepository.findAll());
        return "petSpecies";
    }

    @Secured("ADMIN")
    @RequestMapping(value = "/petSpecies", method = RequestMethod.POST)
    public String create(@ModelAttribute PetSpecies petSpecies) {
        petSpeciesRepository.save(petSpecies);
        return "redirect:/petSpecies";
    }
}
