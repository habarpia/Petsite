package wad.domain;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity
public class Item  extends AbstractPersistable<Long> {
    private String name;
    
    @OneToMany(mappedBy = "item", fetch = FetchType.EAGER)
    private List<InventoryItem> items;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<InventoryItem> getItems() {
        return items;
    }

    public void setItems(List<InventoryItem> items) {
        this.items = items;
    }
    
    public void addItem(InventoryItem inventoryItem){
        if(this.items == null){
            this.items = new ArrayList<InventoryItem>();
        }
        items.add(inventoryItem);
    }
    public void removeItem(InventoryItem item){
        if(this.items != null && this.items.contains(item)){
            items.remove(item);
        }
    }
}
