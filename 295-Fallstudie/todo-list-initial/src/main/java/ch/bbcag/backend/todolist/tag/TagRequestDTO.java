package ch.bbcag.backend.todolist.tag;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Objects;

public class TagRequestDTO {

    @NotBlank(message="must not be blank")
    @NotNull(message="must not be null")
    @Length(min = 3, max = 128, message = "length must be between 6 and 255")
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
