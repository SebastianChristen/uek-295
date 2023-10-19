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

    public List<TagResponseDTO> findAll() {
        return tagRepository.findAll().stream().map(TagMapper::toResponseDTO).toList();
    }

    public TagResponseDTO findById(Integer id) {
        return TagMapper.toResponseDTO(tagRepository.findById(id).orElseThrow(EntityNotFoundException::new));
    }

    public List<TagResponseDTO> findByName(String name) {
        return tagRepository.findByNameContains(name).stream().map(TagMapper::toResponseDTO).toList();
    }

    public TagResponseDTO insert(TagRequestDTO tagRequestDTO) {
        return TagMapper.toResponseDTO(tagRepository.save(TagMapper.fromRequestDTO(tagRequestDTO)));
    }

    public TagResponseDTO update(TagRequestDTO tagRequestDTO, Integer id) {
        Tag existing = tagRepository.findById(id).orElseThrow(EntityNotFoundException::new);

        mergeTag(existing, TagMapper.fromRequestDTO(tagRequestDTO));

        return TagMapper.toResponseDTO(tagRepository.save(existing));
    }

    public void deleteById(Integer id) {
        tagRepository.deleteById(id);
    }

    private void mergeTag(Tag existing, Tag changing) {
        if (changing.getName() != null) {
            existing.setName(changing.getName());
        }
    }

}
