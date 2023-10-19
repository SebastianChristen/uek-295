package ch.bbcag.backend.todolist.item.Item;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;



public interface ItemRepository extends JpaRepository<Item, Integer> {

    List<Item> findByNameContains(String name);

}
