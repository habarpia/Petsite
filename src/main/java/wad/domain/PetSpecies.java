package wad.domain;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity
public class PetSpecies extends AbstractPersistable<Long> {
    private String name;
    
    @OneToMany(mappedBy = "petSpecies", fetch = FetchType.EAGER)
    private List<Pet> pets;
    
    //neutral image
    @OneToOne
    private Image imageN;
    
    //angry image
    @OneToOne
    private Image imageA;
    
    //happy image
    @OneToOne
    private Image imageH;

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

    public Image getImageN() {
        return imageN;
    }

    public void setImageN(Image image) {
        this.imageN = image;
    }

    public Image getImageA() {
        return imageA;
    }

    public void setImageA(Image imageA) {
        this.imageA = imageA;
    }

    public Image getImageH() {
        return imageH;
    }

    public void setImageH(Image imageH) {
        this.imageH = imageH;
    }
    
    
}
/*
Field error in object 'petSpecies' on field 'neutralImage': 
rejected value 
[org.springframework.web.multipart.support.StandardMultipartHttpServletRequest$StandardMultipartFile@78d3aaaa]; 
codes [typeMismatch.petSpecies.neutralImage,typeMismatch.neutralImage,typeMismatch.wad.domain.Image,typeMismatch]; 
arguments [org.springframework.context.support.DefaultMessageSourceResolvable: 
codes [petSpecies.neutralImage,neutralImage]; 
arguments []; default message [neutralImage]]; 
default message 
[Failed to convert property value of type 
'org.springframework.web.multipart.support.StandardMultipartHttpServletRequest$StandardMultipartFile'
to required type 'wad.domain.Image' for property 'neutralImage'; 
nested exception is java.lang.IllegalStateException: 
Cannot convert value of type 
'org.springframework.web.multipart.support.StandardMultipartHttpServletRequest$StandardMultipartFile'
to required type 'wad.domain.Image' for property 'neutralImage': 
no matching editors or conversion strategy found]
*/