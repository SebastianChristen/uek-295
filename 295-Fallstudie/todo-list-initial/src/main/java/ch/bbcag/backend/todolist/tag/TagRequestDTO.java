package ch.bbcag.backend.todolist.tag;

import java.util.Objects;

public class TagRequestDTO {

    private String name;

    public TagRequestDTO() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof TagRequestDTO tagRequestDTO)) {
            return false;
        }

        return name.equals(tagRequestDTO.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
