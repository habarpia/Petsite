package wad.controller;

import java.util.Collection;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import wad.domain.Pet;
import wad.domain.User;
import wad.repository.InventoryItemRepository;
import wad.repository.UserRepository;
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
    private InventoryItemRepository inventoryItemRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PetService petService;
    
    @RequestMapping(value = "/pets", method = RequestMethod.GET)
    public String list(Model model, @ModelAttribute Pet pet) {
        
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        
        model.addAttribute("pets", petService.getPetsByOwner(auth.getName()));
        model.addAttribute("allPetSpecies", petSpeciesRepository.findAll());
        
        return "pets";
    }

    @RequestMapping(value = "/pets", method = RequestMethod.POST)
    public String create(@Valid @ModelAttribute Pet pet, BindingResult bindingResult, @RequestParam(value = "petSpeciesId") Long petSpeciesId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        
        if(bindingResult.hasErrors()) {
            return "pets";
        }

        petService.save(pet, petSpeciesId, auth.getName());        
        return "redirect:/pets";
    }
    
    @RequestMapping("pets/{id}")
    public String getOne(Model model, @PathVariable String id) {
        if(petRepository.getOne(Long.valueOf(id)) == null) {
            return "redirect:/";
        }
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByUsername(auth.getName());
        Pet pet = petRepository.getOne(Long.valueOf(id));
        model.addAttribute("items", inventoryItemRepository.findByUser(user));
        model.addAttribute("pet", pet);
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
    
    @RequestMapping(value = "feedpet/{id}", method = RequestMethod.POST)
    public String feed(Model model, @PathVariable String id, @RequestParam(value = "itemId") String itemId, RedirectAttributes redirectAttrs) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String message = petService.feedPet(Long.valueOf(id), auth.getName(), Long.valueOf(itemId));

        redirectAttrs.addFlashAttribute("message", message);
        return "redirect:/pets/{id}";
    }
    
}
