package ch.bbcag.backend.todolist.tag;

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

@RestController
@RequestMapping("/tags")
public class TagController {


    private final TagService tagService;

    @Autowired
    public TagController(TagService tagService) {
        this.tagService = tagService;
    }


    @GetMapping(path = "{id}")
    @Operation(summary = "find a tag by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "tag found",
                    content = @Content(schema = @Schema(implementation = TagResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "tag not found",
                    content = @Content)

    })
    public ResponseEntity<?> findById(@Parameter(description = "id of tag") @PathVariable Integer id) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(tagService.findById(id));
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping()
    @Operation(summary = "add a tag")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "tag created",
                    content = @Content(schema = @Schema(implementation = TagResponseDTO.class))),
            @ApiResponse(responseCode = "409", description = "Tag could not be created",
                    content = @Content)
    })
    public ResponseEntity<?> insert(@Valid @Parameter(description = "content of new tag") @RequestBody TagRequestDTO tagRequestDTO) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(tagService.insert(tagRequestDTO));
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }
    }

    @DeleteMapping("{id}")
    @Operation(summary = "delete a tag by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "tag deleted",
                    content = @Content(schema = @Schema(implementation = TagResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "tag not found; not deleted",
                    content = @Content)

    })
    public ResponseEntity<?> deleteById(@Parameter(description = "id of tag") @PathVariable Integer id) {
        try {
            tagService.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (EmptyResultDataAccessException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Tag could not be deleted");
        }
    }


    @GetMapping()
    @Operation(summary = "find a tag by name")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "tag found",
                    content = @Content(schema = @Schema(implementation = TagResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "tag not found",
                    content = @Content)

    })
    public ResponseEntity<?> findByName(@Parameter(description = "name of tag") @RequestParam(required = false) String name) {
        if (StringUtils.isBlank(name)) {
            return ResponseEntity.ok(tagService.findAll());
        }
        return ResponseEntity.ok(tagService.findByName(name));
    }

    @PatchMapping("{id}")
    @Operation(summary = "update a tag by name")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tag found",
                    content = @Content(schema = @Schema(implementation = TagResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Tag not found",
                    content = @Content),
            @ApiResponse(responseCode = "409", description = "Tag not updated, conflict",
                    content = @Content),
    })
    public ResponseEntity<?> update(@Parameter(description = "content of updated of tag") @RequestBody TagRequestDTO tagRequestDTO, @Parameter(description = "id of tag") @PathVariable Integer id) {
        try {
            return ResponseEntity.ok(tagService.update(tagRequestDTO, id));
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }


}
