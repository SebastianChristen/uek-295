package ch.bbcag.backend.todolist.person;

import java.util.List;
import java.util.Objects;

public class PersonResponseDTO extends PersonRequestDTO {
    private Integer id;



    private List<Integer> list; //TODO --> Hier stecken gebleiben, variable heisst list

    public List<Integer> getList() {
        return list;
    }

    public void setList(List<Integer> list) {
        this.list = list;
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

        return super.equals(obj) && id.equals(personResponseDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id);
    }
}
