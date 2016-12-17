package wad.service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wad.domain.Pet;
import wad.domain.PetSpecies;
import wad.domain.User;
import wad.repository.PetRepository;
import wad.repository.UserRepository;
import wad.repository.PetSpeciesRepository;

@Service
public class PetService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PetSpeciesRepository petSpeciesRepository;
    @Autowired
    private PetRepository petRepository;
    
    @Transactional
    public void save(Pet pet, Long petSpeciesId, String username){
        pet.setLastFed(new Timestamp(new Date().getTime()));
        petRepository.save(pet);
        assignPetSpecies(pet, petSpeciesId);
        assignPetUser(pet, username);
    }
    
    @Transactional
    private void assignPetUser(Pet pet, String username){
        User user = userRepository.findByUsername(username);
        
        user.addPet(pet);
        pet.setUser(user);
    }
    
    @Transactional
    private void assignPetSpecies(Pet pet, Long petSpeciesId){
        PetSpecies petSpecies = petSpeciesRepository.findOne(petSpeciesId);
        
        pet.setPetSpecies(petSpecies);
        petSpecies.addPet(pet);
    }
    
    @Transactional
    public void deletePet(Long petId, String username){
        Pet pet = petRepository.findOne(petId);
        User loggedInUser = userRepository.findByUsername(username);
        
        if(Objects.equals(pet.getUser().getId(), loggedInUser.getId())){
            User owner = userRepository.findOne(pet.getUser().getId());
            PetSpecies petSpecies = petSpeciesRepository.findOne(pet.getPetSpecies().getId());

            owner.removePet(pet);
            petSpecies.removePet(pet);
            petRepository.delete(pet);
        }
    }
    
    public List<Pet> getPetsByOwner(String username){
        User user = userRepository.findByUsername(username);
                
        return user.getPets();
    }
    
    public String feedPet(Long petId, String username){
        Pet pet = petRepository.findOne(petId);
        if(pet == null){
            return "Pet doesn't exist!";
        }
        User loggedInUser = userRepository.findByUsername(username);
        
        if(!Objects.equals(pet.getUser().getId(), loggedInUser.getId())){
            return "You can only feed your own pet!";
        }
        pet.setFullness(calculateHunger(pet));
        if(pet.getFullness() >= 10){
            return pet.getName() + " is already totally full!";
        }
        if(pet.getHappiness() <= 200){
            pet.setHappiness(pet.getHappiness() + 1);
            pet.setFullness(pet.getFullness() + 1);
            pet.setLastFed(new Timestamp(new Date().getTime()));
            petRepository.save(pet);
        }
        return "You fed " + pet.getName() + "!";
        
    }

    //kylläisyys laskee joka viides minuutti
    private int calculateHunger(Pet pet){
        Timestamp lastFed = pet.getLastFed();
        Timestamp now = new Timestamp(new Date().getTime());
        int fullness = pet.getFullness();
        
        long diff = now.getTime() - lastFed.getTime();
        
        while(diff > 300000 && fullness > 0){
            diff = diff - 300000;
            fullness --;
        }
        return fullness;
    }
}
