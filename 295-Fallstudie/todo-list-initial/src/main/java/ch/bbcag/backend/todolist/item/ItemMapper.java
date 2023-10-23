package ch.bbcag.backend.todolist.item;
import ch.bbcag.backend.todolist.person.Person;
import ch.bbcag.backend.todolist.tag.TagResponseDTO;

public class ItemMapper {


    public static ItemResponseDTO toResponseDTO(Item item) {
        ItemResponseDTO itemResponseDTO = new ItemResponseDTO();
        TagResponseDTO tag = new TagResponseDTO();

        itemResponseDTO.setId(item.getId());
        itemResponseDTO.setCreatedAt(item.getCreatedAt());
        itemResponseDTO.setDeletedAt(item.getDeletedAt());
        itemResponseDTO.setDoneAt(item.getDoneAt());
        itemResponseDTO.setDescription(item.getDescription());
        //itemResponseDTO.setPersonId(item.getPersonId());
        itemResponseDTO.setName(item.getName());
        itemResponseDTO.setPersonId(item.getPerson().getId());
        itemResponseDTO.setTagIds(tag.getItemIds()); /* <-------- TODO prolly falsch idk... */

        return itemResponseDTO;

    }

    public static Item fromRequestDTO(ItemRequestDTO itemRequestDTO) {
        Item item = new Item();
        item.setName(itemRequestDTO.getName());
        item.setDescription(itemRequestDTO.getDescription());
        //item.setId(itemRequestDTO.getId());
        //item.setCreatedAt(itemRequestDTO.getCreatedAt());
        item.setDeletedAt(itemRequestDTO.getDeletedAt());
        item.setDoneAt(itemRequestDTO.getDoneAt());
        item.setDeletedAt(itemRequestDTO.getDeletedAt());

        if (itemRequestDTO.getPersonId() != null) {
            item.setPerson(new Person(itemRequestDTO.getPersonId()));
        }

        return item;


    }


}
