package ch.bbcag.backend.todolist.item;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;



public interface ItemRepository extends JpaRepository<Item, Integer> {

    List<Item> findByNameContains(String name);


    @Query("SELECT i FROM Item i " + "JOIN i.linkedTags t " + "WHERE t.name LIKE CONCAT('%', :tagName, '%')")
    List<Item> findByTagName(@Param("tagName") String tagName);


    @Query(
            "SELECT i FROM Item i " +
            "JOIN i.linkedTags t " +
            "WHERE i.name LIKE CONCAT('%', :name, '%') " +
            "AND t.name LIKE CONCAT('%', :tagName, '%') ")
    List<Item> findByNameAndTagName(@Param("name") String name, @Param("tagName") String tagName);



}
