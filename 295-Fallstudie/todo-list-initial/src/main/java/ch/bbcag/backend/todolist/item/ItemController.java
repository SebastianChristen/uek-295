package ch.bbcag.backend.todolist.item;

import ch.bbcag.backend.todolist.person.PersonResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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


    @GetMapping(path = "{id}")
    @Operation(summary = "Get an item by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Item found",
                    content = @Content(schema = @Schema(implementation = ItemResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Item not found",
                    content = @Content)

    })
    public ResponseEntity<?> findById(@Parameter(description = "Id of user to get") @PathVariable Integer id) {
        try {
            return ResponseEntity.status(HttpStatus.FOUND).body(itemService.findById(id));
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping()
    @Operation(summary = "add an item")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Item created",
                    content = @Content(schema = @Schema(implementation = ItemResponseDTO.class))),
            @ApiResponse(responseCode = "409", description = "Item could not be created",
                    content = @Content)

    })
    public ResponseEntity<?> insert(@Valid @Parameter(description = "Updated information") @RequestBody ItemRequestDTO newItem) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(itemService.insert(newItem));
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }
    }

    @DeleteMapping("{id}")
    @Operation(summary = "delete an item by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Item deleted",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Item not found",
                    content = @Content)

    })
    public ResponseEntity<?> deleteById(@Parameter(description = "Id of user to delete") @PathVariable Integer id) {
        try {
            itemService.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (EmptyResultDataAccessException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Item could not be deleted");
        }
    }


    @GetMapping
    @Operation(summary = "Get an item by name")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Item found",
                    content = @Content(schema = @Schema(implementation = ItemResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Item not found",
                    content = @Content)

    })
    public ResponseEntity<?> findByName(@Parameter(description = "name of item") @RequestParam(required = false) String name, @Parameter(description = "name of tag") @RequestParam(required = false) String tagName) {
        if (StringUtils.isNotBlank(name) && StringUtils.isNotBlank(tagName)) {
            return ResponseEntity.ok(itemService.findByNameAndTagName(name, tagName));
        } else if (StringUtils.isNotBlank(tagName)) {
            return ResponseEntity.ok(itemService.findByTagName(tagName));
        } else if (StringUtils.isNotBlank(name)) {
            return ResponseEntity.ok(itemService.findByName(name));
        }
        return ResponseEntity.ok(itemService.findAll());
    }

    @PatchMapping("{id}")
    @Operation(summary = "update an item")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Item found",
                    content = @Content(schema = @Schema(implementation = ItemResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Item not found",
                    content = @Content),
            @ApiResponse(responseCode = "409", description = "item not updated, conflict",
                    content = @Content),

    })
    public ResponseEntity<?> update(@Parameter(description = "Item content to update") @RequestBody ItemRequestDTO item, @Parameter(description = "id of item to update") @PathVariable Integer id) {
        try {
            return ResponseEntity.ok(itemService.update(item, id));
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }


}
