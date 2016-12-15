package wad.domain;

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
}
