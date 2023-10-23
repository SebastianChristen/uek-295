package ch.bbcag.backend.todolist.tag;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;

@RestController
@RequestMapping("/tags")
public class TagController {


    private final TagService tagService;

    @Autowired
    public TagController(TagService tagService) {
        this.tagService = tagService;
    }


    @GetMapping(path = "{id}")
    public ResponseEntity<?> findById(@PathVariable Integer id) {
        try {
            return ResponseEntity.status(HttpStatus.FOUND).body(tagService.findById(id));
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping()
    public ResponseEntity<?> insert(@Valid @RequestBody TagRequestDTO tagRequestDTO) {
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
        if (StringUtils.isBlank(name)) {
            return ResponseEntity.ok(tagService.findAll());
        }
        return ResponseEntity.ok(tagService.findByName(name));
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
