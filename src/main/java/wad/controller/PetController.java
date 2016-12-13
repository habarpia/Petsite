package wad.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import wad.domain.Pet;
import wad.domain.PetSpecies;
import wad.repository.PetRepository;
import wad.repository.PetSpeciesRepository;
import wad.service.PetService;

@Controller
public class PetController {
    @Autowired
    private PetRepository petRepository;
    @Autowired
    private PetSpeciesRepository petSpeciesRepository;
    @Autowired
    private PetService petService;
    
    @RequestMapping(value = "/pets", method = RequestMethod.GET)
    public String list(Model model) {
        model.addAttribute("pets", petRepository.findAll());
        model.addAttribute("allPetSpecies", petSpeciesRepository.findAll());
        
        return "pets";
    }

    @RequestMapping(value = "/pets", method = RequestMethod.POST)
    public String create(@ModelAttribute Pet pet, @RequestParam(value = "petSpeciesId") Long petSpeciesId) {
        petRepository.save(pet);
        petService.assignPetSpecies(pet, petSpeciesId);
        
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        petService.assignPetUser(pet, username);
        
        return "redirect:/pets";
    }
    
}
