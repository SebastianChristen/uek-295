package ch.bbcag.backend.todolist.item;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.EntityNotFoundException;


//TODO nicht itemRepository, sondern SERVICE
//requestparameter falls es nicht id ist. id wird mit Path angegeben

@RestController
@RequestMapping("/items")
public class ItemController {

    private final ItemService itemService;

    @Autowired
    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }


    @GetMapping("{id}")
    public ResponseEntity<?> findById(@PathVariable Integer id) {
        try {
            return ResponseEntity.status(HttpStatus.FOUND).body(itemService.findById(id));
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping()
    public ResponseEntity<?> insert(@RequestBody ItemRequestDTO newItem ) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(itemService.insert(newItem));
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteById(@PathVariable Integer id) {
        try {
            itemService.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (EmptyResultDataAccessException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Item could not be deleted");
        }
    }



    @GetMapping()
    public ResponseEntity<?> findByName(@RequestParam(required=false) String name) {
        if (!(name==null || name.isBlank())) {
            return ResponseEntity.status(HttpStatus.FOUND).body(itemService.findByName(name));
        } else {
            return ResponseEntity.status(HttpStatus.FOUND).body(itemService.findAll());
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



}
