package ch.bbcag.backend.todolist.utils;

import ch.bbcag.backend.todolist.item.ItemRequestDTO;
import ch.bbcag.backend.todolist.item.ItemResponseDTO;
import ch.bbcag.backend.todolist.person.PersonRequestDTO;
import ch.bbcag.backend.todolist.person.PersonResponseDTO;
import ch.bbcag.backend.todolist.security.AuthRequestDTO;
import ch.bbcag.backend.todolist.security.AuthResponseDTO;
import ch.bbcag.backend.todolist.security.JwtResponseDTO;
import ch.bbcag.backend.todolist.tag.TagRequestDTO;
import ch.bbcag.backend.todolist.tag.TagResponseDTO;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class TestDataDTOUtil {
    public static PersonResponseDTO getTestUserResponseDTO() {
        return getTestUserResponseDTOs().get(0);
    }

    public static PersonResponseDTO getInvalidTestUserResponseDTO() {
        PersonResponseDTO personResponseDTO = new PersonResponseDTO();
        personResponseDTO.setId(0);
        return personResponseDTO;
    }

    public static List<PersonResponseDTO> getTestUserResponseDTOs() {
        List<PersonResponseDTO> userDTOs = new ArrayList<>();

        for (int i = 1; i <= 4; i++) {
            PersonResponseDTO personResponseDTO = new PersonResponseDTO();
            personResponseDTO.setId(i);
            personResponseDTO.setUsername("User" + i);

            List<Integer> itemIds = new ArrayList<>();
            itemIds.add(i);

            personResponseDTO.setItemIds(itemIds);

            userDTOs.add(personResponseDTO);
        }

        return userDTOs;
    }

    public static PersonRequestDTO getTestUserRequestDTO() {
        return getTestUserRequestDTOs().get(0);
    }

    public static List<PersonRequestDTO> getTestUserRequestDTOs() {
        List<PersonRequestDTO> personRequestDTOS = new ArrayList<>();

        for (int i = 0; i <= 4; i++) {
            PersonRequestDTO personRequestDTO = new PersonRequestDTO();
            personRequestDTO.setUsername("User" + i);
            personRequestDTO.setPassword("Password" + 1);

            personRequestDTOS.add(personRequestDTO);
        }

        return personRequestDTOS;
    }

    public static ItemRequestDTO getTestItemRequestDTO() {
        return getTestItemRequestDTOs().get(0);
    }

    public static List<ItemRequestDTO> getTestItemRequestDTOs() {
        List<ItemRequestDTO> itemRequestDTOs = new ArrayList<>();
        List<PersonResponseDTO> users = getTestUserResponseDTOs();

        for (int i = 1; i <= 4; i++) {
            ItemRequestDTO itemRequestDTO = new ItemRequestDTO();
            itemRequestDTO.setName("Item" + i);
            itemRequestDTO.setDescription("Description" + i);

            itemRequestDTO.setDoneAt(Timestamp.valueOf("2020-01-01 05:00:00"));
            itemRequestDTO.setDeletedAt(Timestamp.valueOf("2020-01-01 10:00:00"));

            itemRequestDTO.setPersonId(users.get(0).getId());
            if (i > 3) {
                itemRequestDTO.setPersonId(users.get(1).getId());
            }
            itemRequestDTOs.add(itemRequestDTO);
        }
        return itemRequestDTOs;
    }

    public static ItemResponseDTO getTestItemResponseDTO() {
        return getTestItemResponseDTOs().get(0);
    }

    public static List<ItemResponseDTO> getTestItemResponseDTOs() {
        List<ItemResponseDTO> itemResponseDTOs = new ArrayList<>();
        List<PersonResponseDTO> users = getTestUserResponseDTOs();
        List<Integer> tagIds = new ArrayList<>();
        tagIds.add(1);

        for (int i = 1; i <= 4; i++) {
            ItemResponseDTO itemResponseDTO = new ItemResponseDTO();

            itemResponseDTO.setId(i);
            itemResponseDTO.setName("Item" + i);
            itemResponseDTO.setDescription("Description" + i);

            itemResponseDTO.setCreatedAt(Timestamp.valueOf("2020-01-01 00:00:00"));
            itemResponseDTO.setDoneAt(Timestamp.valueOf("2020-01-01 05:00:00"));
            itemResponseDTO.setDeletedAt(Timestamp.valueOf("2020-01-01 10:00:00"));

            itemResponseDTO.setPersonId(users.get(0).getId());
            if (i > 3) {
                itemResponseDTO.setPersonId(users.get(1).getId());
            }

            itemResponseDTO.setTagIds(tagIds);

            itemResponseDTOs.add(itemResponseDTO);
        }

        return itemResponseDTOs;
    }

    public static TagRequestDTO getTestTagRequestDTO() {
        return getTestTagRequestDTOs().get(0);
    }

    public static List<TagRequestDTO> getTestTagRequestDTOs() {
        List<TagRequestDTO> tagRequestDTOs = new ArrayList<>();

        for (int i = 1; i <= 4; i++) {
            TagRequestDTO tagRequestDTO = new TagRequestDTO();
            tagRequestDTO.setName("Tag" + i);
            tagRequestDTOs.add(tagRequestDTO);
        }
        return tagRequestDTOs;
    }

    public static TagResponseDTO getTestTagResponseDTO() {
        return getTestTagResponseDTOs().get(0);
    }

    public static List<TagResponseDTO> getTestTagResponseDTOs() {
        List<TagResponseDTO> tagResponseDTOs = new ArrayList<>();
        List<Integer> itemIds = new ArrayList<>();
        itemIds.add(1);

        for (int i = 1; i <= 4; i++) {
            TagResponseDTO tagResponseDTO = new TagResponseDTO();
            tagResponseDTO.setId(i);
            tagResponseDTO.setName("Tag" + i);
            tagResponseDTO.setItemIds(itemIds);
            tagResponseDTOs.add(tagResponseDTO);
        }
        return tagResponseDTOs;
    }

    public static AuthResponseDTO getTestAuthResponseDTO() {
        AuthResponseDTO authResponseDTO = new AuthResponseDTO();
        authResponseDTO.setId(1);
        authResponseDTO.setUsername("User1");
        return authResponseDTO;
    }

    public static AuthRequestDTO getTestAuthRequestDTO() {
        AuthRequestDTO authResponseDTO = new AuthRequestDTO();
        authResponseDTO.setUsername("User1");
        return authResponseDTO;
    }

    public static JwtResponseDTO getTestJwtResponseDTO() {
        return new JwtResponseDTO("TokenASDF", 1, "User1");
    }
}
