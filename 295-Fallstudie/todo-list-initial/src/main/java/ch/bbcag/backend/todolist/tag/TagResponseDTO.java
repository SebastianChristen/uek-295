package ch.bbcag.backend.todolist.tag;

import java.util.List;
import java.util.Objects;

public class TagResponseDTO extends TagRequestDTO {

    private Integer id;
    private List<Integer> itemIds;

    public TagResponseDTO() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<Integer> getItemIds() {
        return itemIds;
    }

    public void setItemIds(List<Integer> itemIds) {
        this.itemIds = itemIds;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof TagResponseDTO tagResponseDTO)) {
            return false;
        }

        return super.equals(obj)
                && id.equals(tagResponseDTO.id) && itemIds.equals(tagResponseDTO.itemIds);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id, itemIds);
    }
}
