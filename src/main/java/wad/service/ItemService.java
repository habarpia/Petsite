package wad.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wad.domain.InventoryItem;
import wad.domain.Item;
import wad.domain.Pet;
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
    
    @Transactional
    public String getRandomItems(String username){
        User user = userRepository.findByUsername(username);
        if(user.getItems().size() >= 20){
            return "Your inventory is already full!";
        }
        Item item = findRandomItemFromDb();
        
        InventoryItem inventoryItem = new InventoryItem();
        inventoryItem.setItem(item);
        inventoryItem.setUser(user);
        inventoryItemRepository.save(inventoryItem);
        item.addItem(inventoryItem);
        user.addItem(inventoryItem);
        userRepository.save(user);
        itemRepository.save(item);
        
        return item.getName() + " obtained!";
    }
    
    @Transactional
    public void removeInventoryItem(Long id){
        InventoryItem inventoryItem = inventoryItemRepository.findOne(id);
        User user = userRepository.findOne(inventoryItem.getUser().getId());
        Item item = itemRepository.findOne(inventoryItem.getItem().getId());
        
        user.removeItem(inventoryItem);
        item.removeItem(inventoryItem);
        
        inventoryItemRepository.delete(inventoryItem);
        user = userRepository.save(user);
        item = itemRepository.save(item);
    }
    
    private Item findRandomItemFromDb(){
        Pageable pageable = new PageRequest(0, 1);
        return itemRepository.findRandomItem(pageable).getContent().get(0);
    }
    
    //lemmikille 2 lempiruokaa ja 2 inhokkiruokaa
    public void setPreferences(Pet pet){
        Set<Item> generatedSet = new HashSet<Item>();
        while(generatedSet.size() < 4){
            generatedSet.add(findRandomItemFromDb());
        }
        Object[] generatedArray = generatedSet.toArray();
        ArrayList<Object> generatedArrayList = new ArrayList<Object>(Arrays.asList(generatedArray));
        Collections.shuffle(generatedArrayList);
        
        int i = 0;
        while(i < generatedArrayList.size()/2){
            pet.addLikedItem((Item)generatedArrayList.get(i));
            pet.addDislikedItem((Item)generatedArrayList.get(generatedArrayList.size()-1-i));
            i++;
        }
    }
    
    public Item getItem(Long id){
        return itemRepository.findOne(id);
    }
    public long getItemIdForInventoryItem(long inventoryItemId){
        return inventoryItemRepository.findOne(inventoryItemId).getItem().getId();
    }
}
