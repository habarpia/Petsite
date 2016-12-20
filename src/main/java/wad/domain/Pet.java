package wad.domain;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.ManyToMany;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity
public class Pet extends AbstractPersistable<Long> {
    @NotBlank
    @Length(min = 1, max = 20)
    private String name;
    
    @ManyToOne
    private User user;
    
    @ManyToOne
    private PetSpecies petSpecies;
    
    @ManyToMany
    private List<Item> likedItems;
    
    @ManyToMany
    private List<Item> dislikedItems;
    
    private int happiness;
    private int fullness;
    private Timestamp lastFed;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public PetSpecies getPetSpecies() {
        return petSpecies;
    }

    public void setPetSpecies(PetSpecies petSpecies) {
        this.petSpecies = petSpecies;
    }

    public int getHappiness() {
        return happiness;
    }

    public void setHappiness(int happiness) {
        this.happiness = happiness;
    }

    public int getFullness() {
        return fullness;
    }

    public void setFullness(int fullness) {
        this.fullness = fullness;
    }

    public Timestamp getLastFed() {
        return lastFed;
    }

    public void setLastFed(Timestamp lastFed) {
        this.lastFed = lastFed;
    }

    public List<Item> getLikedItems() {
        return likedItems;
    }

    public void setLikedItems(List<Item> likedItems) {
        this.likedItems = likedItems;
    }

    public List<Item> getDislikedItems() {
        return dislikedItems;
    }

    public void setDislikedItems(List<Item> dislikedItems) {
        this.dislikedItems = dislikedItems;
    }
    
    public void addLikedItem(Item item){
        if(this.likedItems == null){
            this.likedItems = new ArrayList<Item>();
        }
        likedItems.add(item);
    }
    
    public void removeLikedItem(Item item){
        if(this.likedItems != null && this.likedItems.contains(item)){
            likedItems.remove(item);
        }
    }
    
    public void addDislikedItem(Item item){
        if(this.dislikedItems == null){
            this.dislikedItems = new ArrayList<Item>();
        }
        dislikedItems.add(item);
    }
    
    public void removeDislikedItem(Item item){
        if(this.dislikedItems != null && this.dislikedItems.contains(item)){
            dislikedItems.remove(item);
        }
    }
}
