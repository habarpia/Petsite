package wad.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import wad.domain.InventoryItem;

public interface InventoryItemRepository  extends JpaRepository<InventoryItem, Long>{
    
}
