package wad.domain;

import java.sql.Timestamp;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity
public class Pet extends AbstractPersistable<Long> {
    private String name;
    
    @ManyToOne
    private User user;
    
    @ManyToOne
    private PetSpecies petSpecies;
    
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
}
