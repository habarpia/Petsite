package wad.service;

import java.util.List;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import wad.domain.Pet;
import wad.domain.PetSpecies;
import wad.domain.User;
import wad.repository.PetRepository;
import wad.repository.PetSpeciesRepository;
import wad.repository.UserRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PetServiceTest {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PetSpeciesRepository petSpeciesRepository;
    @Autowired
    private PetRepository petRepository;
    @Autowired
    private PetService petService;
    
    @Test
    public void lemmikkiTallentuu() {
        User user = new User();
        user.setUsername("username");
        user.setEmail("example@gmail.com");
        user.setPassword("password");
        
        PetSpecies petSpecies = new PetSpecies();
        petSpecies.setName("pupu");
        
        petSpeciesRepository.save(petSpecies);
        userRepository.save(user);
        
        Pet pet = new Pet();
        pet.setName("Pupuna");
        
        petService.save(pet, petSpecies.getId(), user.getUsername());

        Pet retrieved = petRepository.findOne(pet.getId());

        assertNotNull(retrieved);
        assertEquals("Pupuna", retrieved.getName());
        assertEquals(retrieved.getUser().getId(), user.getId());
        assertEquals(retrieved.getPetSpecies().getId(), petSpecies.getId());
    }
    
    @Test
    public void lemmikkiDeletoituu() {
        User user = new User();
        user.setUsername("foobar");
        user.setEmail("foo@gmail.com");
        user.setPassword("password");
        
        PetSpecies petSpecies = new PetSpecies();
        petSpecies.setName("pupu");
        
        petSpeciesRepository.save(petSpecies);
        userRepository.save(user);
        
        Pet pet = new Pet();
        pet.setName("Pupuna");
        
        petService.save(pet, petSpecies.getId(), user.getUsername());

        Pet retrieved = petRepository.findOne(pet.getId());
        
        petService.deletePet(retrieved.getId(), user.getUsername());
        
        PetSpecies retrievedSpecies = petSpeciesRepository.findOne(retrieved.getPetSpecies().getId());
        User retrievedUser = userRepository.findOne(retrieved.getUser().getId());
        
        assertFalse(retrievedUser.getPets().contains(retrieved));
        assertFalse(retrievedSpecies.getPets().contains(retrieved));
    }
    
    @Test
    public void vainOmanLemmikinVoiTuhota(){
        User user = new User();
        user.setUsername("owner");
        user.setEmail("owner@gmail.com");
        user.setPassword("password");
        
        User otherUser = new User();
        otherUser.setUsername("other");
        otherUser.setEmail("other@gmail.com");
        otherUser.setPassword("password");
        
        PetSpecies petSpecies = new PetSpecies();
        petSpecies.setName("pupu");
        
        petSpeciesRepository.save(petSpecies);
        userRepository.save(user);
        userRepository.save(otherUser);
        
        Pet pet = new Pet();
        pet.setName("Pupuna");
        
        petService.save(pet, petSpecies.getId(), user.getUsername());
        
        Pet retrieved = petRepository.findOne(pet.getId());
        
        petService.deletePet(retrieved.getId(), otherUser.getUsername());
        User retrievedUser = userRepository.findOne(retrieved.getUser().getId());
        
        assertTrue(retrievedUser.getPets().contains(retrieved));
    }
    
    @Test
    public void kaikkiOmistajanLemmikitPalautetaan(){
        User user = new User();
        user.setUsername("correctOwner");
        user.setEmail("owner@gmail.com");
        user.setPassword("password");
        
        User otherUser = new User();
        otherUser.setUsername("otherOwner");
        otherUser.setEmail("other@gmail.com");
        otherUser.setPassword("password");
        
        PetSpecies petSpecies = new PetSpecies();
        petSpecies.setName("pupu");
        
        petSpeciesRepository.save(petSpecies);
        userRepository.save(user);
        userRepository.save(otherUser);
        
        Pet pet = new Pet();
        pet.setName("Pupuna");
        Pet pet2 = new Pet();
        pet.setName("Puputti");
        Pet pet3 = new Pet();
        pet.setName("Väiski");
        
        petService.save(pet, petSpecies.getId(), user.getUsername());
        petService.save(pet2, petSpecies.getId(), user.getUsername());
        petService.save(pet3, petSpecies.getId(), otherUser.getUsername());
        
        List<Pet> retrieved = petService.getPetsByOwner(user.getUsername());
        assertTrue(retrieved.contains(petRepository.findOne(pet.getId())));
        assertTrue(retrieved.contains(petRepository.findOne(pet2.getId())));
        assertFalse(retrieved.contains(petRepository.findOne(pet3.getId())));
    }      
}
