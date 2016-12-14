//package wad.service;
//
//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertFalse;
//import static org.junit.Assert.assertNotNull;
//import static org.junit.Assert.assertTrue;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;
//import wad.domain.Pet;
//import wad.domain.PetSpecies;
//import wad.domain.User;
//import wad.repository.PetRepository;
//import wad.repository.PetSpeciesRepository;
//import wad.repository.UserRepository;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest
//public class PetServiceTest {
//    @Autowired
//    private UserRepository userRepository;
//    @Autowired
//    private PetSpeciesRepository petSpeciesRepository;
//    @Autowired
//    private PetRepository petRepository;
//    @Autowired
//    private PetService petService;
//    
//    private User user;
//    private PetSpecies petSpecies;
//    
//    @Before
//    public void initialize() {
//        user = new User();
//        user.setUsername("username");
//        
//        petSpecies = new PetSpecies();
//        petSpecies.setName("pupu");
//        
//        petSpeciesRepository.save(petSpecies);
//        userRepository.save(user);
//    }
//    
//    @Test
//    public void lemmikkiTallentuujaDeletoituu() {
//        Pet pet = new Pet();
//        pet.setName("Pupuna");
//        
//        petService.save(pet, petSpecies.getId(), user.getUsername());
//
//        Pet retrieved = petRepository.findOne(pet.getId());
//
//        assertNotNull(retrieved);
//        assertEquals("Pupuna", retrieved.getName());
//        assertEquals(retrieved.getUser().getId(), user.getId());
//        assertEquals(retrieved.getPetSpecies().getId(), petSpecies.getId());
//        
//        petService.deletePet(retrieved.getId());
//        
//        PetSpecies retrievedSpecies = petSpeciesRepository.findOne(retrieved.getPetSpecies().getId());
//        User retrievedUser = userRepository.findOne(retrieved.getUser().getId());
//        
//        assertFalse(retrievedUser.getPets().contains(retrieved));
//        assertFalse(retrievedSpecies.getPets().contains(retrieved));
//    }
//        
//}
