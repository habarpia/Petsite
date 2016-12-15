package wad.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
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
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        
        model.addAttribute("pets", petService.getPetsByOwner(auth.getName()));
        model.addAttribute("allPetSpecies", petSpeciesRepository.findAll());
        
        return "pets";
    }

    @RequestMapping(value = "/pets", method = RequestMethod.POST)
    public String create(@ModelAttribute Pet pet, @RequestParam(value = "petSpeciesId") Long petSpeciesId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        
        petService.save(pet, petSpeciesId, auth.getName());        
        return "redirect:/pets";
    }
    
    @RequestMapping("pets/{id}")
    public String getOne(Model model, @PathVariable String id) {
        if(petRepository.getOne(Long.valueOf(id)) == null) {
            return "redirect:/";
        }

        model.addAttribute("pet", petRepository.getOne(Long.valueOf(id)));
        return "pet";
    }
    
    @RequestMapping(value = "pets/{id}", method = RequestMethod.DELETE)
    public String delete(@PathVariable String id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(petRepository.getOne(Long.valueOf(id)) != null) {
            petService.deletePet(Long.valueOf(id), auth.getName());
        }

        return "redirect:/pets";
    }
    
}
