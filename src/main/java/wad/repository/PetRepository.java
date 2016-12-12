package wad.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import wad.domain.Pet;

public interface PetRepository  extends JpaRepository<Pet, Long>{
    
}
