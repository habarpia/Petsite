package wad.controller;

import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import wad.domain.Image;
import wad.domain.PetSpecies;
import wad.repository.PetSpeciesRepository;
import wad.service.ImageService;

@Controller
public class PetSpeciesController {
    @Autowired
    PetSpeciesRepository petSpeciesRepository;
    @Autowired
    private ImageService imageService;
    
    @RequestMapping(value = "/petSpecies", method = RequestMethod.GET)
    public String list(Model model) {
        model.addAttribute("allPetSpecies", petSpeciesRepository.findAll());
        return "petSpecies";
    }

    //@Secured("ADMIN")
    @RequestMapping(value = "/petSpecies", method = RequestMethod.POST)
    public String create(@ModelAttribute PetSpecies petSpecies, 
            @RequestParam("metadata") String metadata,
            @RequestParam("file") MultipartFile file) throws IOException{
        Image image = new Image();
        image.setMetadata(metadata);
        petSpecies.setImage(image);

        imageService.add(image, file.getContentType(), file.getOriginalFilename(), file.getBytes());
        petSpeciesRepository.save(petSpecies);
        
        return "redirect:/petSpecies";
    }
}
