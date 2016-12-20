package wad.service;

import static org.junit.Assert.assertTrue;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import wad.domain.Item;
import wad.domain.User;
import wad.repository.InventoryItemRepository;
import wad.repository.ItemRepository;
import wad.repository.UserRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ItemServiceTest {
    @Autowired
    ItemRepository itemRepository;
    @Autowired
    InventoryItemRepository inventoryItemRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    ItemService itemService;
    
    @Test
    @Transactional
    public void possibleToGetItems(){
        User user = new User();
        user.setUsername("username1");
        user.setEmail("example1@gmail.com");
        user.setPassword("password");
        user = userRepository.save(user);
        
        Item item = new Item();
        item.setName("Banana");
        itemRepository.save(item);
        Item item2 = new Item();
        item2.setName("Cake");
        itemRepository.save(item2);
        
        itemService.getRandomItems(user.getUsername());
        
        User retrieved = userRepository.findByUsername(user.getUsername());
        assertTrue(retrieved.getItems().size() >= 0);
        assertTrue(retrieved.getItems().get(0).getItem().getName() != null);
    }
}
