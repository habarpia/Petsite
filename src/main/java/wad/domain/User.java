package wad.domain;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.FetchType;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.data.jpa.domain.AbstractPersistable;
import org.springframework.security.crypto.bcrypt.BCrypt;

@Entity
public class User extends AbstractPersistable<Long>  {
    @NotBlank
    @Length(min = 1, max = 20)
    @Column(unique = true)
    private String username;
    @NotBlank
    private String password;
    private String salt;
    @NotBlank
    @Email
    @Column(unique = true)
    private String email;
    
    @ElementCollection()
    private List<String> authorities;
    
    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    private List<Pet> pets;
    
    @OneToMany(mappedBy = "user")
    private List<InventoryItem> items;
    
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
        this.salt = BCrypt.gensalt();
        this.password = BCrypt.hashpw(password, this.salt);
    }
    
    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
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
    public List<String> getAuthorities() {
        return authorities;
    }
 
    public void setAuthorities(List<String> authorities) {
        this.authorities = authorities;
    } 
    
    public void addAuthority(String authority){
        if(this.authorities == null){
            this.authorities = new ArrayList<String>();
        }
        authorities.add(authority);
    }

    public List<InventoryItem> getItems() {
        if(this.items == null) {
            this.items = new ArrayList<>();
        }
        
        return items;
    }

    public void setItems(List<InventoryItem> items) {
        this.items = items;
    }
    
    public void addItem(InventoryItem item){
        if(this.items == null){
            this.items = new ArrayList<InventoryItem>();
        }
        items.add(item);
    }
    
    public void removeItem(InventoryItem item){
        if(this.items != null && this.items.contains(item)){
            items.remove(item);
        }
    }
}
