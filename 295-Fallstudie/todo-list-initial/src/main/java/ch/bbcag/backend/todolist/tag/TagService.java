package ch.bbcag.backend.todolist.tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class TagService {

    private final TagRepository tagRepository;


    @Autowired
    public TagService(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    public List<TagResponseDTO> findByName(String name) {
        List<Tag> tags = tagRepository.findByNameContains(name);
        return tags.stream().map(TagMapper::toResponseDTO).toList();
    }

    public List<TagResponseDTO> findAll() {
        List<Tag> tags = tagRepository.findAll();
        return tags.stream().map(TagMapper::toResponseDTO).toList();
    }

    public TagResponseDTO findById(Integer id) {
        return TagMapper.toResponseDTO(tagRepository.findById(id).orElseThrow(EntityNotFoundException::new));
    }

    public void deleteById(Integer id) {
        tagRepository.deleteById(id);
    }

    public TagResponseDTO insert(TagRequestDTO tagRequestDTO) {
        return TagMapper.toResponseDTO(tagRepository.save(TagMapper.fromRequestDTO(tagRequestDTO)));
    }


    public TagResponseDTO update(TagRequestDTO tagRequestDTO, Integer tagId) {
        Tag existingTag = tagRepository.findById(tagId).orElseThrow(EntityNotFoundException::new);

        mergeTags(existingTag, TagMapper.fromRequestDTO(tagRequestDTO));

        return TagMapper.toResponseDTO(tagRepository.save(existingTag));
    }


    private void mergeTags(Tag existingTag, Tag changingTag) {
        if (changingTag.getName() != null) {
            existingTag.setName(changingTag.getName());
        }

    }


}