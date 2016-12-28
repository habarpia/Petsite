package wad.repository;

import org.springframework.data.domain.Pageable;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import wad.domain.Item;

public interface ItemRepository  extends JpaRepository<Item, Long>{
    @Query("SELECT i FROM Item i order by RAND()")
    List <Item> findTop1RandomItem();
    
    @Query("SELECT i FROM Item i order by RAND()")
    Page<Item> findRandomItem(Pageable pageable);
}
