package wad.domain;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity
public class PetSpecies extends AbstractPersistable<Long> {
    private String name;
    
    @OneToMany(mappedBy = "petSpecies", fetch = FetchType.EAGER)
    private List<Pet> pets;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Pet> getPets() {
        return pets;
    }

    public void setPets(List<Pet> pets) {
        this.pets = pets;
    }
    
    public void addPet(Pet pet){
        if(this.pets == null){
            this.pets = new ArrayList<Pet>();
        }
        pets.add(pet);
    }
    
    public void removePet(Pet pet){
        if(this.pets != null){
            pets.remove(pet);
        }
    }
    
    
}
