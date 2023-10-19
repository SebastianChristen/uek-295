package ch.bbcag.backend.todolist.item;

import java.sql.Timestamp;
import java.util.Objects;

public class ItemResponseDTO extends ItemRequestDTO {
    private Integer id;
    private Timestamp createdAt;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ItemResponseDTO itemResponseDTO)) {
            return false;
        }

        return super.equals(obj)
                && id.equals(itemResponseDTO.id)
                && createdAt.equals(itemResponseDTO.createdAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id, createdAt);
    }
}
