package wad.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import wad.domain.InventoryItem;
import wad.domain.User;

public interface InventoryItemRepository  extends JpaRepository<InventoryItem, Long>{
    List<InventoryItem> findByUser(User user);
}
