package ch.bbcag.backend.todolist.tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.EntityNotFoundException;

@RestController
@RequestMapping("/tags")
public class TagController {


    private final TagService tagService;

    @Autowired
    public TagController(TagService tagService) {
        this.tagService = tagService;
    }


    @GetMapping("{id}")
    public ResponseEntity<?> findById(@PathVariable Integer id) {
        try {
            return ResponseEntity.status(HttpStatus.FOUND).body(tagService.findById(id));
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping()
    public ResponseEntity<?> insert(@RequestBody TagRequestDTO tagRequestDTO) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(tagService.insert(tagRequestDTO));
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Tag was not found");
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteById(@PathVariable Integer id) {
        try {
            tagService.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (EmptyResultDataAccessException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Tag could not be deleted");
        }
    }


    @GetMapping()
    public ResponseEntity<?> findByName(@RequestParam(required = false) String name) {
        if (!(name == null || name.isBlank())) {
            return ResponseEntity.status(HttpStatus.FOUND).body(tagService.findByName(name));
        } else {
            return ResponseEntity.status(HttpStatus.FOUND).body(tagService.findAll());
        }
    }

    @PatchMapping("{id}")
    public ResponseEntity<?> update(@RequestBody TagRequestDTO tagRequestDTO, @PathVariable Integer id) {
        try {
            return ResponseEntity.ok(tagService.update(tagRequestDTO, id));
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }


}
