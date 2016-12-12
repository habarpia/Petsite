package wad.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import wad.domain.PetSpecies;

public interface PetSpeciesRepository  extends JpaRepository<PetSpecies, Long>{
    
}
