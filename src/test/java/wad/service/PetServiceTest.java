package wad.service;

import java.util.List;
import org.junit.After;
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
import org.springframework.transaction.annotation.Transactional;
import wad.domain.InventoryItem;
import wad.domain.Item;
import wad.domain.Pet;
import wad.domain.PetSpecies;
import wad.domain.User;
import wad.repository.InventoryItemRepository;
import wad.repository.ItemRepository;
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
    private ItemRepository itemRepository;
    @Autowired
    private InventoryItemRepository inventoryItemRepository;
    @Autowired
    private PetService petService;
    @Autowired
    private ItemService itemService;
    
    @Test
    public void lemmikkiTallentuu() {
        User user = new User();
        user.setUsername("usernametest");
        user.setEmail("example@maili.com");
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
        otherUser.setEmail("otherowner@gmail.com");
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
        user.setUsername("correctOwner1");
        user.setEmail("owner1@owner.com");
        user.setPassword("password");
        
        User otherUser = new User();
        otherUser.setUsername("otherOwner2");
        otherUser.setEmail("other2@other.com");
        otherUser.setPassword("password");
        
        PetSpecies petSpecies = new PetSpecies();
        petSpecies.setName("pupu");
        
        petSpeciesRepository.save(petSpecies);
        userRepository.save(user);
        userRepository.save(otherUser);
        
        Pet pet = new Pet();
        pet.setName("Pupuna");
        Pet pet2 = new Pet();
        pet2.setName("Puputti");
        Pet pet3 = new Pet();
        pet3.setName("Väiski");
        
        petService.save(pet, petSpecies.getId(), user.getUsername());
        petService.save(pet2, petSpecies.getId(), user.getUsername());
        petService.save(pet3, petSpecies.getId(), otherUser.getUsername());
        
        List<Pet> retrieved = petService.getPetsByOwner(user.getUsername());
        assertTrue(retrieved.contains(petRepository.findOne(pet.getId())));
        assertTrue(retrieved.contains(petRepository.findOne(pet2.getId())));
        assertFalse(retrieved.contains(petRepository.findOne(pet3.getId())));
    }  

    @Test
    @Transactional
    public void lemmikinVoiRuokkia(){
        User user = new User();
        user.setUsername("ruokkija");
        user.setEmail("ruokkija@maili.com");
        user.setPassword("password");

        PetSpecies petSpecies = new PetSpecies();
        petSpecies.setName("pupu");

        Item item = new Item();
        item.setName("Päärynä");
        InventoryItem inventoryItem = new InventoryItem();
        inventoryItem.setItem(item);
        inventoryItem.setUser(user);
        user.addItem(inventoryItem);
        item.addItem(inventoryItem);

        Item item2 = new Item();
        item2.setName("Omena");
        Item item3 = new Item();
        item3.setName("Banaani");
        Item item4 = new Item();
        item4.setName("Kiivi");

        petSpeciesRepository.save(petSpecies);
        userRepository.save(user);
        itemRepository.save(item);
        itemRepository.save(item2);
        itemRepository.save(item3);
        itemRepository.save(item4);
        inventoryItemRepository.save(inventoryItem);

        Pet pet = new Pet();
        pet.setName("Pupuna");

        petService.save(pet, petSpecies.getId(), user.getUsername());
        petService.feedPet(pet.getId(), user.getUsername(), inventoryItem.getId());

        Pet retrieved = petRepository.findOne(pet.getId());
        assertEquals(retrieved.getFullness(), 1);
    }
}
