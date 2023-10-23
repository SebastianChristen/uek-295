package ch.bbcag.backend.todolist.controllers;

import ch.bbcag.backend.todolist.person.PersonController;
import ch.bbcag.backend.todolist.person.PersonRequestDTO;
import ch.bbcag.backend.todolist.person.PersonResponseDTO;
import ch.bbcag.backend.todolist.person.PersonService;
import ch.bbcag.backend.todolist.utils.TestDataDTOUtil;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.web.servlet.MockMvc;

import javax.persistence.EntityNotFoundException;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = PersonController.class)
@AutoConfigureMockMvc(addFilters = false)
public class PersonControllerTest {

    private static final String JSON_ALL_USERS = """
            [
              {
                "id": 1,
                "username": "User1",
                "itemIds": [
                    1
                ]
              },
              {
                "id": 2,
                "username": "User2",
                "itemIds": [
                    2
                ]
              },
              {
                "id": 3,
                "username": "User3",
                "itemIds": [
                    3
                ]
              },
              {
                "id": 4,
                "username": "User4",
                "itemIds": [
                    4
                ]
              }
            ]
            """;
    private final ArgumentCaptor<PersonRequestDTO> argumentCaptorPerson = ArgumentCaptor.forClass(PersonRequestDTO.class);
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private PersonService personService;

    @Test
    public void checkGet_whenNoParam_thenAllPersonsAreReturned() throws Exception {
        List<PersonResponseDTO> expected = TestDataDTOUtil.getTestUserResponseDTOs();

        Mockito.when(personService.findAll()).thenReturn(expected);

        mockMvc.perform(get(PersonController.PATH))
                .andExpect(status().isOk())
                .andExpect(content().json(JSON_ALL_USERS));
    }

    @Test
    public void checkFindById_whenValidId_thenPersonIsReturned() throws Exception {
        PersonResponseDTO expected = TestDataDTOUtil.getTestUserResponseDTO();

        Mockito.when(personService.findById(eq(1))).thenReturn(expected);

        mockMvc.perform(get(PersonController.PATH + "/" + 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username", is("User1")));
    }

    @Test
    public void checkFindById_whenInvalidId_thenIsNotFound() throws Exception {
        Mockito.when(personService.findById(eq(0))).thenThrow(EntityNotFoundException.class);

        mockMvc.perform(get(PersonController.PATH + "/" + 0))
                .andExpect(status().isNotFound());
    }

    @Test
    public void checkDelete_whenValidId_thenIsNoContent() throws Exception {
        mockMvc.perform(delete(PersonController.PATH + "/" + 1))
                .andExpect(status().isNoContent());

        Mockito.verify(personService).deleteById(eq(1));
    }

    @Test
    public void checkDelete_whenInvalidId_thenIsNotFound() throws Exception {
        Mockito.doThrow(EmptyResultDataAccessException.class).when(personService).deleteById(0);

        mockMvc.perform(delete(PersonController.PATH + "/" + 0))
                .andExpect(status().isNotFound());

        Mockito.verify(personService).deleteById(0);
    }

}
