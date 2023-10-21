package ch.bbcag.backend.todolist.tag;

import ch.bbcag.backend.todolist.item.Item;

import java.util.List;

public class TagMapper {

    public static TagResponseDTO toResponseDTO(Tag tag) {
        TagResponseDTO tagResponseDTO = new TagResponseDTO();

        tagResponseDTO.setId(tag.getId());
        tagResponseDTO.setName(tag.getName());

        if (tag.getId() != null) { /* TODO eventuell noch fehler        v */
            List<Integer> itemIds = tag.getLinkedItems().stream().map(Item::getId).toList();
            tagResponseDTO.setItemIds(itemIds);
        }

        return tagResponseDTO;
    }

    public static Tag fromRequestDTO(TagRequestDTO tagRequestDTO) {

        Tag tag = new Tag();

        tag.setName(tagRequestDTO.getName());
        //tag.setLinkedItems(tagRequestDTO.getLinkedItems()); /*<--- gibt es nicht???? Tag soll leer erstellt werden*/

        return tag;

    }

}
