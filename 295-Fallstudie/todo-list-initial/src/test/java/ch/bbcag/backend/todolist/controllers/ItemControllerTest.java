package ch.bbcag.backend.todolist.controllers;

import ch.bbcag.backend.todolist.item.ItemController;
import ch.bbcag.backend.todolist.item.ItemResponseDTO;
import ch.bbcag.backend.todolist.item.ItemService;
import ch.bbcag.backend.todolist.utils.TestDataDTOUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.web.servlet.MockMvc;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = ItemController.class)
@AutoConfigureMockMvc(addFilters = false)
public class ItemControllerTest {
    private static final String JSON_ALL_ITEMS = """
            [
              {
                "id": 1,
                "name": "Item1",
                "description": "Description1",
                "createdAt": "2020-01-01T00:00:00.000+00:00",
                "doneAt": "2020-01-01T05:00:00.000+00:00",
                "deletedAt": "2020-01-01T10:00:00.000+00:00",
                "personId": 1,
                "tagIds": [
                  1
                ]
              },
              {
                "id": 2,
                "name": "Item2",
                "description": "Description2",
                "createdAt": "2020-01-01T00:00:00.000+00:00",
                "doneAt": "2020-01-01T05:00:00.000+00:00",
                "deletedAt": "2020-01-01T10:00:00.000+00:00",
                "personId": 1,
                "tagIds": [
                  1
                ]
              },
              {
                "id": 3,
                "name": "Item3",
                "description": "Description3",
                "createdAt": "2020-01-01T00:00:00.000+00:00",
                "doneAt": "2020-01-01T05:00:00.000+00:00",
                "deletedAt": "2020-01-01T10:00:00.000+00:00",
                "personId": 1,
                "tagIds": [
                  1
                ]
              },
              {
                "id": 4,
                "name": "Item4",
                "description": "Description4",
                "createdAt": "2020-01-01T00:00:00.000+00:00",
                "doneAt": "2020-01-01T05:00:00.000+00:00",
                "deletedAt": "2020-01-01T10:00:00.000+00:00",
                "personId": 2,
                "tagIds": [
                  1
                ]
              }
            ]
            """;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ItemService itemService;

    @BeforeEach
    public void prepare() {
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
    }

    @Test
    public void checkGet_whenNoParam_thenAllItemsAreReturned() throws Exception {
        List<ItemResponseDTO> expected = TestDataDTOUtil.getTestItemResponseDTOs();

        Mockito.when(itemService.findAll()).thenReturn(expected);

        mockMvc.perform(get(ItemController.PATH))
                .andExpect(status().isOk())
                .andExpect(content().json(JSON_ALL_ITEMS));
    }

    @Test
    public void checkGet_whenValidName_thenItemIsReturned() throws Exception {
        String itemName = "Item4";

        List<ItemResponseDTO> expected = TestDataDTOUtil.getTestItemResponseDTOs().subList(3, 4);

        Mockito.when(itemService.findByName(eq(itemName))).thenReturn(expected);

        mockMvc.perform(get(ItemController.PATH)
                        .contentType("application/json")
                        .queryParam("name", itemName))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value(itemName));
    }

    @Test
    public void checkGet_whenValidTagName_thenItemIsReturned() throws Exception {
        String itemName = "Item4";
        String tagName = "Tag1";

        List<ItemResponseDTO> expected = TestDataDTOUtil.getTestItemResponseDTOs().subList(3, 4);

        Mockito.when(itemService.findByTagName(eq(tagName))).thenReturn(expected);

        mockMvc.perform(get(ItemController.PATH)
                        .contentType("application/json")
                        .queryParam("tagName", tagName))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value(itemName));
    }

    @Test
    public void checkGet_whenValidItemNameAndTagName_thenItemIsReturned() throws Exception {
        String itemName = "Item4";
        String tagName = "Tag1";

        List<ItemResponseDTO> expected = TestDataDTOUtil.getTestItemResponseDTOs().subList(3, 4);

        Mockito.when(itemService.findByNameAndTagName(eq(itemName), eq(tagName))).thenReturn(expected);

        mockMvc.perform(get(ItemController.PATH)
                        .contentType("application/json")
                        .queryParam("name", itemName)
                        .queryParam("tagName", tagName))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value(itemName));
    }

    @Test
    public void checkGet_whenNotExistingName_thenIsOkAndEmpty() throws Exception {
        String name = "NotExistingItem";

        List<ItemResponseDTO> expected = new ArrayList<>();

        Mockito.when(itemService.findByNameAndTagName(eq(name), eq(null))).thenReturn(expected);

        mockMvc.perform(get(ItemController.PATH)
                        .contentType("application/json")
                        .queryParam("name", name))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    public void checkFindById_whenValidId_thenItemIsReturned() throws Exception {
        ItemResponseDTO expected = TestDataDTOUtil.getTestItemResponseDTO();

        Mockito.when(itemService.findById(eq(1))).thenReturn(expected);

        mockMvc.perform(get(ItemController.PATH + "/" + 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Item1")));
    }

    @Test
    public void checkFindById_whenInvalidId_thenIsNotFound() throws Exception {
        Mockito.when(itemService.findById(eq(0))).thenThrow(EntityNotFoundException.class);

        mockMvc.perform(get(ItemController.PATH + "/" + 0))
                .andExpect(status().isNotFound());
    }

    @Test
    public void checkPost_whenValidNewItem_thenIsCreated() throws Exception {
        Mockito.when(itemService.insert(any())).thenReturn(TestDataDTOUtil.getTestItemResponseDTO());

        mockMvc.perform(post(ItemController.PATH)
                        .contentType("application/json")
                        .content("{\"name\":\"Item1\", \"personId\":\"1\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", is("Item1")));
    }


    @Test
    public void checkDelete_whenValidId_thenIsNoContent() throws Exception {
        mockMvc.perform(delete(ItemController.PATH + "/" + 1))
                .andExpect(status().isNoContent());

        Mockito.verify(itemService).deleteById(eq(1));
    }

    @Test
    public void checkDelete_whenInvalidId_thenIsNotFound() throws Exception {
        // in this order because itemService.deleteById() returns void - can not be used inside when() as in other tests
        Mockito.doThrow(EmptyResultDataAccessException.class).when(itemService).deleteById(0);

        mockMvc.perform(delete(ItemController.PATH + "/" + 0))
                .andExpect(status().isNotFound());

        Mockito.verify(itemService).deleteById(eq(0));
    }

}
