package ch.bbcag.backend.todolist.item.Item;

import ch.bbcag.backend.todolist.person.PersonService;
import nonapi.io.github.classgraph.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.EntityNotFoundException;
import java.util.List;


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
    public ResponseEntity<?> insert(@RequestBody ItemResponseDTO newItem ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(itemService.insert(newItem));
    }

    @DeleteMapping("{id}")
    public void deleteById(@PathVariable Integer id) {
       itemService.deleteById(id);
    }



    @GetMapping()
    public ResponseEntity<?> findByName(@RequestParam(required=false) String name) {
        if (!name.isBlank()) {
            return ResponseEntity.status(HttpStatus.FOUND).body(itemService.findByName(name));
        } else {
            return ResponseEntity.status(HttpStatus.FOUND).body(itemService.findAll());
        }
    }




}
