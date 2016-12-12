package wad.service;

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
    private PetRepository petRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PetSpeciesRepository petSpeciesRepository;
    
    @Transactional
    public void assignPetUser(Long userId, Long PetId){
        //todo
    }
    
    @Transactional
    public void assignPetSpecies(Pet pet, Long petSpeciesId){
        //Pet pet = petRepository.findOne(petId);
        PetSpecies petSpecies = petSpeciesRepository.findOne(petSpeciesId);
        
        pet.setPetSpecies(petSpecies);
        petSpecies.addPet(pet);
    }
}
