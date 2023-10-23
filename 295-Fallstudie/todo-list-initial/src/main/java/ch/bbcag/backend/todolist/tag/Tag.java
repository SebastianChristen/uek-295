package ch.bbcag.backend.todolist.tag;

import ch.bbcag.backend.todolist.item.Item;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
public class Tag {

    @ManyToMany(mappedBy = "linkedTags")
    private Set<Item> linkedItems = new HashSet<>();

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true)
    private String name;


    public Tag() {
    }

    public Tag(Integer id) {
        this.id = id;
    }

    public Set<Item> getLinkedItems() {
        return linkedItems;
    }

    public void setLinkedItems(Set<Item> linkedItems) {
        this.linkedItems = linkedItems;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /*NUR ID WIRD VERGLICHEN*/
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tag tag = (Tag) o;
        return Objects.equals(id, tag.id);
    }

    /*NUR ID WIRD GEHASHT*/
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
