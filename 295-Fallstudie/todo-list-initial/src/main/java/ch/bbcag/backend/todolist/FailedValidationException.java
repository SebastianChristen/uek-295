package ch.bbcag.backend.todolist;

import java.util.List;
import java.util.Map;

public class FailedValidationException extends RuntimeException{

    public FailedValidationException(Map<String, List<String>> errors) {
        if (!errors.isEmpty()) { throw new FailedValidationException(errors); }
    }

}
