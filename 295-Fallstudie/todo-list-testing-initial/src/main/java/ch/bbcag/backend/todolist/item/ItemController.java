package ch.bbcag.backend.todolist.item;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.EntityNotFoundException;

@RestController
@RequestMapping(ItemController.PATH)
public class ItemController {
    public static final String PATH = "/items";

    private final ItemService itemService;

    @Autowired
    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping
    public ResponseEntity<?> findItems(@RequestParam(required = false) String name, @RequestParam(required = false) String tagName) {
        if (StringUtils.isNotBlank(name) && StringUtils.isNotBlank(tagName)) {
            return ResponseEntity.ok(itemService.findByNameAndTagName(name, tagName));
        } else if (StringUtils.isNotBlank(tagName)) {
            return ResponseEntity.ok(itemService.findByTagName(tagName));
        } else if (StringUtils.isNotBlank(name)) {
            return ResponseEntity.ok(itemService.findByName(name));
        }
        return ResponseEntity.ok(itemService.findAll());
    }

    @GetMapping(path = "{id}")
    public ResponseEntity<?> findById(@PathVariable Integer id) {
        try {
            return ResponseEntity.ok(itemService.findById(id));
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping()
    public ResponseEntity<?> insert(@RequestBody ItemRequestDTO newItem) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(itemService.insert(newItem));
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }
    }

    @PatchMapping("{id}")
    public ResponseEntity<?> update(@RequestBody ItemRequestDTO item, @PathVariable Integer id) {
        try {
            return ResponseEntity.ok(itemService.update(item, id));
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        try {
            itemService.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (EmptyResultDataAccessException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Item could not be deleted");
        }
    }

}
