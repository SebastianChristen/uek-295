package ch.bbcag.backend.todolist.item;

import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;

public class ItemRequestDTO {
    private String description;
    private Timestamp deletedAt;
    private Integer personId;
    private String name;
    private Timestamp doneAt;
    private List<Integer> tagIds;

    public ItemRequestDTO() {
    }

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

    public List<Integer> getTagIds() {
        return tagIds;
    }

    public void setTagIds(List<Integer> tagIds) {
        this.tagIds = tagIds;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ItemRequestDTO itemRequestDTO)) {
            return false;
        }

        return personId.equals(itemRequestDTO.personId)
                && name.equals(itemRequestDTO.name)
                && description.equals(itemRequestDTO.description)
                && doneAt.equals(itemRequestDTO.doneAt)
                && deletedAt.equals(itemRequestDTO.deletedAt)
                && tagIds.equals(itemRequestDTO.tagIds);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, personId, description, doneAt, deletedAt, tagIds);
    }
}
