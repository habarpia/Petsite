package wad.service;

import java.util.List;
import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wad.domain.InventoryItem;
import wad.domain.Item;
import wad.domain.User;
import wad.repository.InventoryItemRepository;
import wad.repository.ItemRepository;
import wad.repository.UserRepository;

@Service
public class ItemService {
    @Autowired
    ItemRepository itemRepository;
    @Autowired
    InventoryItemRepository inventoryItemRepository;
    @Autowired
    UserRepository userRepository;
    private Random randomGenerator;
    
    @Transactional
    public void getRandomItems(String username){
        User user = userRepository.findByUsername(username);
        if(user.getItems().size() >= 20){
            return;
        }
        Item item = getRandomItem();
        
        InventoryItem inventoryItem = new InventoryItem();
        inventoryItem.setItem(item);
        inventoryItem.setUser(user);
        inventoryItemRepository.save(inventoryItem);
        item.addItem(inventoryItem);
        user.addItem(inventoryItem);
        userRepository.save(user);
        itemRepository.save(item);
    }
    
    private Item getRandomItem(){
        if(randomGenerator ==null){
            randomGenerator = new Random();
        }
        List<Item> items = itemRepository.findAll();
        return items.get(randomGenerator.nextInt(items.size()));
    }
}
