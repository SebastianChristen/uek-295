package ch.bbcag.backend.todolist.item.Item;

import ch.bbcag.backend.todolist.person.Person;
import ch.bbcag.backend.todolist.person.PersonMapper;
import ch.bbcag.backend.todolist.person.PersonRepository;
import ch.bbcag.backend.todolist.person.PersonResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
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
        return itemRepository.findByNameContains(name).stream().map(ItemMapper::toResponseDTO).toList();
    }

    public List<ItemResponseDTO> findAll(){
        return itemRepository.findAll().stream().map(ItemMapper::toResponseDTO).toList();
    }
    public void deleteById(Integer id){
        itemRepository.deleteById(id);
    }
    public ItemResponseDTO insert(ItemRequestDTO item){

        //---- Irgendwie so etwas, nicht sicher...
        //Item nonexistent = new Item();
        //nonexistent.setDescription(ItemRequestDTO.getDescription();

        return ItemMapper.toResponseDTO(itemRepository.save(item)); //TODO <------ Hier stehen geblieben
    }


    public ItemResponseDTO update(ItemRequestDTO itemRequestDTO, Integer itemId){
        Item existing = itemRepository.findById(itemId).orElseThrow(EntityNotFoundException::new);

        mergeItems(existing, ItemMapper.fromRequestDTO(itemRequestDTO));

        return ItemMapper.toResponseDTO(itemRepository.save(existing));}


    private void mergeItems(Item existing, Item changing) {
        if (changing.getName() != null) {
            existing.setName(changing.getName());
        }

    }

}
