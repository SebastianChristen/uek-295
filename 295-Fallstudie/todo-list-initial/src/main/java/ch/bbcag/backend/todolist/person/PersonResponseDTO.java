package ch.bbcag.backend.todolist.person;

import java.util.List;
import java.util.Objects;

public class PersonResponseDTO extends PersonRequestDTO {
    private Integer id;



    private List<Integer> itemIds; //TOnotDOanymore --> Hier stecken geblieben, variable heisst itemIds

    public List<Integer> getItemIds() {
        return itemIds;
    }

    public void setItemIds(List<Integer> itemIds) {
        this.itemIds = itemIds;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof PersonResponseDTO personResponseDTO)) {
            return false;
        }

        return super.equals(obj) && id.equals(personResponseDTO.id) && itemIds.equals(personResponseDTO.itemIds);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id, itemIds);
    }
}
