package ch.bbcag.backend.todolist.controllers;

import ch.bbcag.backend.todolist.person.PersonResponseDTO;
import ch.bbcag.backend.todolist.person.PersonService;
import ch.bbcag.backend.todolist.security.AuthController;
import ch.bbcag.backend.todolist.security.AuthRequestDTO;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = AuthController.class)
@AutoConfigureMockMvc(addFilters = false)
public class AuthControllerTest {
    private final ArgumentCaptor<AuthRequestDTO> argumentCaptorAuth = ArgumentCaptor.forClass(AuthRequestDTO.class);
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private PersonService personService;
    @MockBean
    private AuthenticationManager authenticationManager;

    @Test
    public void checkPost_whenNewUser_thenIsCreated() throws Exception {
        PersonResponseDTO expected = new PersonResponseDTO();
        expected.setUsername("NewUser");

        Mockito.when(personService.create(any(AuthRequestDTO.class))).thenReturn(expected);

        mockMvc.perform(post(AuthController.PATH + "/signup")
                        .contentType("application/json")
                        .content("{\"username\":\"NewUser\", \"password\":\"newPassword\"}"))
                .andExpect(status().isCreated());


        Mockito.verify(personService).create(argumentCaptorAuth.capture());

        AuthRequestDTO actual = argumentCaptorAuth.getValue();

        assertEquals("NewUser", actual.getUsername());
        assertEquals("newPassword", actual.getPassword());
    }

    @Test
    public void checkPost_whenInvalidUser_thenIsBadRequest() throws Exception {
        mockMvc.perform(post(AuthController.PATH + "/signup")
                        .contentType("application/json")
                        .content("{\"wrongFieldName\":\"User1\"}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void checkPost_whenNoUserName_thenIsBadRequest() throws Exception {
        mockMvc.perform(post(AuthController.PATH + "/signup")
                        .contentType("application/json")
                        .content("{\"username\":\"\", \"password\":\"password\"}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void checkPost_whenNoPassword_thenIsBadRequest() throws Exception {
        mockMvc.perform(post(AuthController.PATH + "/signup")
                        .contentType("application/json")
                        .content("{\"username\":\"username\", \"password\":\"\"}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void checkPost_whenLongBlankPassword_thenIsBadRequest() throws Exception {
        mockMvc.perform(post(AuthController.PATH + "/signup")
                        .contentType("application/json")
                        .content("{\"username\":\"username\", \"password\":\"            \"}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void checkPost_whenToShortPassword_thenIsBadRequest() throws Exception {
        mockMvc.perform(post(AuthController.PATH + "/signup")
                        .contentType("application/json")
                        .content("{\"username\":\"username\", \"password\":\"12345\"}"))
                .andExpect(status().isBadRequest());
    }

}
