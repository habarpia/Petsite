package wad.domain;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
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
    private String email;
    private String authority;
    
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

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }
    
    
}
