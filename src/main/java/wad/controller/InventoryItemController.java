package wad.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import wad.domain.InventoryItem;
import wad.domain.Item;
import wad.domain.User;
import wad.repository.InventoryItemRepository;
import wad.repository.ItemRepository;
import wad.repository.UserRepository;
import wad.service.ItemService;

@Controller
public class InventoryItemController {
    @Autowired
    ItemRepository itemRepository;
    
    @Autowired
    InventoryItemRepository inventoryItemRepository;
    
    @Autowired
    UserRepository userRepository;
    
    @Autowired
    ItemService itemService;
    
    @RequestMapping(value = "/inventory", method = RequestMethod.GET)
    public String list(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByUsername(auth.getName());
        model.addAttribute("items", inventoryItemRepository.findByUser(user));
        return "inventory";
    }
    
    @RequestMapping(value = "/itemGet", method = RequestMethod.POST)
    public String getRandomItems() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        itemService.getRandomItems(auth.getName());
//        User user = userRepository.findByUsername(auth.getName());
//        Item item = itemRepository.findOne(Long.valueOf(1));
//        
//        InventoryItem inventoryItem = new InventoryItem();
//        inventoryItem.setItem(item);
//        inventoryItem.setUser(user);
//        inventoryItemRepository.save(inventoryItem);
//        item.addItem(inventoryItem);
//        user.addItem(inventoryItem);
//        userRepository.save(user);
//        itemRepository.save(item);
        
        return "redirect:/inventory";
    }
}
