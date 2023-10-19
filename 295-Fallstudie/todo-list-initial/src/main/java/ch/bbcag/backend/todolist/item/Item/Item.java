package ch.bbcag.backend.todolist.item.Item;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Objects;

import ch.bbcag.backend.todolist.person.Person;

import javax.persistence.*;


@Entity
public class Item {

    public Item()
    {}

    private String description;
    private String name;

    @Id //TODO wegen id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(insertable = false)
    private Timestamp createdAt;
    private Timestamp deletedAt;
    private Timestamp doneAt;

    @ManyToOne
    @JoinColumn(name = "person_id")
    private Person person;

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(Timestamp deletedAt) {
        this.deletedAt = deletedAt;
    }

    public Timestamp getDoneAt() {
        return doneAt;
    }

    public void setDoneAt(Timestamp doneAt) {
        this.doneAt = doneAt;
    }


    public Person getPerson() {
        return person;
    }


    public void setPerson(Person person) {
        this.person = person;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return id == item.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }


}
