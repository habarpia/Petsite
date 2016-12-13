package wad.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
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
    public void testSavePet() {
//        User user = new User();
//        user.setUsername("username");
//        
//        PetSpecies petSpecies = new PetSpecies();
//        petSpecies.setName("pupu");
//        
//        Pet pet = new Pet();
//        pet.setName("Pupuna");
//        
//        petSpeciesRepository.save(petSpecies);
//        userRepository.save(user);
//        
//        petService.save(pet, petSpecies.getId(), user.getUsername());
//
//        Pet retrieved = petRepository.findOne(pet.getId());
//
//        assertNotNull(retrieved);
//        assertEquals("Pupuna", retrieved.getName());
//        assertEquals(retrieved.getUser().getId(), user.getId());
//        assertEquals(retrieved.getPetSpecies().getId(), petSpecies.getId());
    }
}
