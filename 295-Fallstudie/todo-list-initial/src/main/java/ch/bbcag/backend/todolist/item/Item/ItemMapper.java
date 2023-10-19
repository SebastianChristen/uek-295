package ch.bbcag.backend.todolist.item.Item;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.sql.Timestamp;

public class ItemMapper {


    public static ItemResponseDTO toResponseDTO(Item item) {
        ItemResponseDTO itemResponseDTO = new ItemResponseDTO();
        itemResponseDTO.setId(item.getId());
        itemResponseDTO.setCreatedAt(item.getCreatedAt());
        itemResponseDTO.setDeletedAt(item.getDeletedAt());
        itemResponseDTO.setDoneAt(item.getDoneAt());
        itemResponseDTO.setDescription(item.getDescription());
        //itemResponseDTO.setPersonId(item.getPersonId());
        itemResponseDTO.setName(item.getName());

        if (itemResponseDTO.getId() == null) {
            return null;
        } else {
            return itemResponseDTO;
        }

    }

    public static Item fromRequestDTO(ItemRequestDTO itemRequestDTO) {
        Item item = new Item();
        item.setName(itemRequestDTO.getName());
        item.setDescription(itemRequestDTO.getDescription());
        //item.setId(itemRequestDTO.getId());
        //item.setCreatedAt(itemRequestDTO.getCreatedAt());
        item.setDeletedAt(itemRequestDTO.getDeletedAt());
        item.setDoneAt(itemRequestDTO.getDoneAt());


        return item;


    }


}
