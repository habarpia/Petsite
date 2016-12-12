package wad.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import org.hibernate.validator.constraints.Email;
import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity
public class User extends AbstractPersistable<Long>  {
    private String name;
    //private Email email;
    private String password;
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

//    public Email getEmail() {
//        return email;
//    }
//
//    public void setEmail(Email email) {
//        this.email = email;
//    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    
}
