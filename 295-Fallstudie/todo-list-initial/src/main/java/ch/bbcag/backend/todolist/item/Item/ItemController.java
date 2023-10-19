package ch.bbcag.backend.todolist.item.Item;

import ch.bbcag.backend.todolist.person.PersonService;
import nonapi.io.github.classgraph.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.EntityNotFoundException;
import java.util.List;


//TODO nicht itemRepository, sondern SERVICE
//requestparameter falls es nicht id ist. id wird mit Path angegeben

@RestController
@RequestMapping("/items")
public class ItemController {

    private final ItemRepository itemRepository;

    @Autowired
    public ItemController(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }


    @GetMapping("{id}")
    public Item findById(@PathVariable Integer id) {
        try {
            return itemRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping()
    public Item insert(@RequestBody Item item) {
        return itemRepository.save(item);
    }

    @DeleteMapping("{id}")
    public void deleteById(@PathVariable Integer id) {
        itemRepository.deleteById(id);
    }



    @GetMapping()
    public List<Item> findByName(@RequestParam(required=false) String name) {
        if (!name.isBlank()) {
            return itemRepository.findByNameContains(name);
        } else {
            return itemRepository.findAll();
        }
    }




}
