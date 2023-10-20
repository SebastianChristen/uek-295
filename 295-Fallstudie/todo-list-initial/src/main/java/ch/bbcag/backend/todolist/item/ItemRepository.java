package ch.bbcag.backend.todolist.item;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;



public interface ItemRepository extends JpaRepository<Item, Integer> {

    List<Item> findByNameContains(String name);

}