package ch.bbcag.backend.todolist.controllers;

import ch.bbcag.backend.todolist.tag.TagController;
import ch.bbcag.backend.todolist.tag.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

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
}
