package ch.bbcag.backend.todolist.item;

import java.sql.Timestamp;
import java.util.Objects;

public class ItemRequestDTO {
    private String description;
    private Timestamp deletedAt;
    private Integer personId;
    private String name;
    private Timestamp doneAt;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Timestamp getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(Timestamp deletedAt) {
        this.deletedAt = deletedAt;
    }

    public Integer getPersonId() {
        return personId;
    }

    public void setPersonId(Integer personId) {
        this.personId = personId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Timestamp getDoneAt() {
        return doneAt;
    }

    public void setDoneAt(Timestamp doneAt) {
        this.doneAt = doneAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ItemRequestDTO that = (ItemRequestDTO) o;
        return Objects.equals(description, that.description) && Objects.equals(deletedAt, that.deletedAt) && Objects.equals(personId, that.personId) && Objects.equals(name, that.name) && Objects.equals(doneAt, that.doneAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(description, deletedAt, personId, name, doneAt);
    }
}
