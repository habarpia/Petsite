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
    
    @OneToMany(mappedBy = "likedItems")
    private List<Pet> petsWhoLikeThis;
    
    @OneToMany(mappedBy = "dislikedItems")
    private List<Pet> petsWhoDislikeThis;

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

    public List<Pet> getPetsWhoLikeThis() {
        return petsWhoLikeThis;
    }

    public void setPetsWhoLikeThis(List<Pet> petsWhoLikeThis) {
        this.petsWhoLikeThis = petsWhoLikeThis;
    }

    public List<Pet> getPetsWhoDislikeThis() {
        return petsWhoDislikeThis;
    }

    public void setPetsWhoDislikeThis(List<Pet> petsWhoDislikeThis) {
        this.petsWhoDislikeThis = petsWhoDislikeThis;
    }
    
    public void addPetWhoLikesThis(Pet pet){
        if(this.petsWhoLikeThis == null){
            this.petsWhoLikeThis = new ArrayList<Pet>();
        }
        petsWhoLikeThis.add(pet);
    }
    
    public void removePetWhoLikesThis(Pet pet){
        if(this.petsWhoLikeThis != null && this.petsWhoLikeThis.contains(pet)){
            petsWhoLikeThis.remove(pet);
        }
    }
    
    public void addPetWhoDisikesThis(Pet pet){
        if(this.petsWhoDislikeThis == null){
            this.petsWhoDislikeThis = new ArrayList<Pet>();
        }
        petsWhoDislikeThis.add(pet);
    }
    
    public void removePetWhoDisikesThis(Pet pet){
        if(this.petsWhoDislikeThis != null && this.petsWhoDislikeThis.contains(pet)){
            petsWhoDislikeThis.remove(pet);
        }
    }
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Item)){
            return false;
        }
        if (obj == this){
            return true;
        }
        Item newItem = (Item)obj;
        return newItem.getName().equals(this.name);
    }
}
