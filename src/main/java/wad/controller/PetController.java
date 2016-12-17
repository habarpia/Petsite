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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
//import java.sql.Timestamp;
//import java.util.Date;
import wad.domain.Pet;
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
    
    @RequestMapping(value = "feedpet/{id}", method = RequestMethod.POST)
    public String feed(Model model, @PathVariable String id, RedirectAttributes redirectAttrs) {
//        Pet pet = petRepository.getOne(Long.valueOf(id));
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String actiontext = petService.feedPet(Long.valueOf(id), auth.getName());
//        if(pet != null) {
//            pet.setFullness(calculateHunger(pet));
//            if(pet.getFullness() >= 10){
//                redirectAttrs.addFlashAttribute("actiontext", pet.getName() + " is already totally full!");
//            }
//            else{
//                if(pet.getHappiness() <= 200){
//                    pet.setHappiness(pet.getHappiness() + 1);
//                    pet.setFullness(pet.getFullness() + 1);
//                    pet.setLastFed(new Timestamp(new Date().getTime()));
//                    petRepository.save(pet);
//                }
//                redirectAttrs.addFlashAttribute("actiontext", "You fed " + pet.getName() + "!");
//            }
//        }
        redirectAttrs.addFlashAttribute("actiontext", actiontext);
        return "redirect:/pets/{id}";
    }
    
    //kylläisyys laskee joka viides minuutti
//    private int calculateHunger(Pet pet){
//        Timestamp lastFed = pet.getLastFed();
//        Timestamp now = new Timestamp(new Date().getTime());
//        int fullness = pet.getFullness();
//        
//        long diff = now.getTime() - lastFed.getTime();
//        
//        while(diff > 300000 && fullness > 0){
//            diff = diff - 300000;
//            fullness --;
//        }
//        return fullness;
//    }
}
