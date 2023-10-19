package ch.bbcag.backend.todolist.item;

import ch.bbcag.backend.todolist.person.Person;
import ch.bbcag.backend.todolist.tag.Tag;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ItemMapper {
    public static ItemResponseDTO toResponseDTO(Item item) {
        ItemResponseDTO itemResponseDTO = new ItemResponseDTO();

        itemResponseDTO.setId(item.getId());

        itemResponseDTO.setName(item.getName());
        itemResponseDTO.setDescription(item.getDescription());

        itemResponseDTO.setCreatedAt(item.getCreatedAt());
        itemResponseDTO.setDoneAt(item.getDoneAt());
        itemResponseDTO.setDeletedAt(item.getDeletedAt());

        itemResponseDTO.setPersonId(item.getPerson().getId());

        if (item.getLinkedTags() != null) {
            List<Integer> tagIds = item
                    .getLinkedTags()
                    .stream()
                    .map(Tag::getId)
                    .toList();

            itemResponseDTO.setTagIds(tagIds);
        }

        return itemResponseDTO;
    }

    public static Item fromRequestDTO(ItemRequestDTO itemRequestDTO) {
        Item item = new Item();

        item.setName(itemRequestDTO.getName());
        item.setDescription(itemRequestDTO.getDescription());

        item.setDoneAt(itemRequestDTO.getDoneAt());
        item.setDeletedAt(itemRequestDTO.getDeletedAt());

        if (itemRequestDTO.getPersonId() != null) {
            item.setPerson(new Person(itemRequestDTO.getPersonId()));
        }

        if (itemRequestDTO.getTagIds() != null) {
            Set<Tag> linkedTags = itemRequestDTO
                    .getTagIds()
                    .stream()
                    .map(Tag::new)
                    .collect(Collectors.toSet());

            item.setLinkedTags(linkedTags);
        }

        return item;
    }

}
