package wad.service;

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
}
