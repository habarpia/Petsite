package wad.domain;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.FetchType;
import org.hibernate.validator.constraints.Email;
import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity
public class User extends AbstractPersistable<Long>  {
    private String username;
    private String password;
    @Email
    private String email;
    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    private List<Pet> pets;
    
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Pet> getPets() {
        return pets;
    }

    public void setPets(List<Pet> pets) {
        this.pets = pets;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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
