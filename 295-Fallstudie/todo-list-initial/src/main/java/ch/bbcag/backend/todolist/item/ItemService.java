package ch.bbcag.backend.todolist.item;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class ItemService {

    private final ItemRepository itemRepository;

    @Autowired
    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }



    public ItemResponseDTO findById(Integer id){
        return ItemMapper.toResponseDTO(itemRepository.findById(id).orElseThrow(EntityNotFoundException::new));
    }



    public List<ItemResponseDTO> findByName(String name) {
        List<Item> items = itemRepository.findByNameContains(name);
        return items.stream().map(ItemMapper::toResponseDTO).toList();
    }

    public List<ItemResponseDTO> findAll(){
        List<Item> items = itemRepository.findAll();
        return items.stream().map(ItemMapper::toResponseDTO).toList();
    }
    public void deleteById(Integer id){
        itemRepository.deleteById(id);
    }
    public ItemResponseDTO insert(ItemRequestDTO itemRequestDTO) {
        return ItemMapper.toResponseDTO(itemRepository.save(ItemMapper.fromRequestDTO(itemRequestDTO)));
    }


    public ItemResponseDTO update(ItemRequestDTO itemRequestDTO, Integer itemId){
        Item existingItem = itemRepository.findById(itemId).orElseThrow(EntityNotFoundException::new);

        mergeItems(existingItem, ItemMapper.fromRequestDTO(itemRequestDTO));

        return ItemMapper.toResponseDTO(itemRepository.save(existingItem));
    }

    private void mergeItems(Item existingItem, Item changingItem) {
        if (changingItem.getName() != null) {
            existingItem.setName(changingItem.getName());
        }
        if (changingItem.getDescription() != null) {
            existingItem.setDescription(changingItem.getDescription());
        }
        if (changingItem.getDoneAt() != null) {
            existingItem.setDoneAt(changingItem.getDoneAt());
        }
        if (changingItem.getDeletedAt() != null) {
            existingItem.setDeletedAt(changingItem.getDeletedAt());
        }
        if (changingItem.getPerson() != null) {
            existingItem.setPerson(changingItem.getPerson());
        }
        if (changingItem.getTagIds != null) {
            existingItem.setTagIds(changingItem.getTagIds());
        }

    }

}
