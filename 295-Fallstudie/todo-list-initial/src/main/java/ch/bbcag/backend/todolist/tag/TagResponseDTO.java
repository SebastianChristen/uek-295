package ch.bbcag.backend.todolist.tag;

import java.util.List;
import java.util.Objects;

public class TagResponseDTO extends TagRequestDTO {

    private Integer Id;
    private List<Integer> itemIds;

    public TagResponseDTO() {
    }

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public List<Integer> getItemIds() {
        return itemIds;
    }

    public void setItemIds(List<Integer> itemIds) {
        this.itemIds = itemIds;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        TagResponseDTO that = (TagResponseDTO) o;
        return Objects.equals(Id, that.Id) && Objects.equals(itemIds, that.itemIds);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), Id, itemIds);
    }
}
