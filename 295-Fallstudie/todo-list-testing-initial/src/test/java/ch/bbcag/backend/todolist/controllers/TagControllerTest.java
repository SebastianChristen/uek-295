package ch.bbcag.backend.todolist.controllers;

import ch.bbcag.backend.todolist.item.ItemController;
import ch.bbcag.backend.todolist.item.ItemResponseDTO;
import ch.bbcag.backend.todolist.tag.TagController;
import ch.bbcag.backend.todolist.tag.TagResponseDTO;
import ch.bbcag.backend.todolist.tag.TagService;
import ch.bbcag.backend.todolist.utils.TestDataDTOUtil;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.hamcrest.Matchers.is;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = TagController.class)
@AutoConfigureMockMvc(addFilters = false)
public class TagControllerTest {

    private static final String JSON_ALL_TAGS = """
            [
              {
                "id": 1,
                "name": "Tag1",
                "itemIds": [
                    1
                ]
              },
              {
                "id": 2,
                "name": "Tag2",
                "itemIds": [
                    1
                ]
              },
              {
                "id": 3,
                "name": "Tag3",
                "itemIds": [
                    1
                ]
              },
              {
                "id": 4,
                "name": "Tag4",
                "itemIds": [
                    1
                ]
              }
            ]
            """;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TagService tagService;

    // TODO: Tests
    @Test
    public void checkGet_whenNoParam_thenAllTagsAreReturned() throws Exception {
        List<TagResponseDTO> expected = TestDataDTOUtil.getTestTagResponseDTOs();

        Mockito.when(tagService.findAll()).thenReturn(expected);

        mockMvc.perform(get(TagController.PATH))
                .andExpect(status().isOk())
                .andExpect(content().json(JSON_ALL_TAGS));
    }

    @Test
    public void checkPost_whenValidNewTag_thenIsCreated() throws Exception {
        Mockito.when(tagService.insert(any())).thenReturn(TestDataDTOUtil.getTestTagResponseDTO());

        mockMvc.perform(post(TagController.PATH)
                        .contentType("application/json")
                        .content("{\"name\":\"Tag1\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", is("Tag1")));
    }

    @Test
    public void checkPost_whenInvalidTag_thenIsBadRequest() throws Exception {
        Mockito.when(tagService.insert(any())).thenReturn(TestDataDTOUtil.getTestTagResponseDTO());

        mockMvc.perform(post(TagController.PATH)
                        .contentType("application/json")
                        .content("{\"ballz\":\"it dont suck itzelf\"}"))
                .andExpect(status().isBadRequest());
    }
}
