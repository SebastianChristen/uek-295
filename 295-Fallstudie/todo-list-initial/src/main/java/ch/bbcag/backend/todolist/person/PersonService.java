package ch.bbcag.backend.todolist.person;

import ch.bbcag.backend.todolist.FailedValidationException;
import ch.bbcag.backend.todolist.security.AuthRequestDTO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PersonService {
    private final PersonRepository personRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public PersonService(PersonRepository personRepository, PasswordEncoder passwordEncoder) {
        this.personRepository = personRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public PersonResponseDTO create(AuthRequestDTO authRequestDTO) {
        Person person = new Person();
        person.setPassword(passwordEncoder.encode(authRequestDTO.getPassword()));
        person.setUsername(authRequestDTO.getUsername());
        return PersonMapper.toResponseDTO(personRepository.save(person));
    }

    public List<PersonResponseDTO> findAll() {
        return personRepository.findAll().stream().map(PersonMapper::toResponseDTO).toList();
    }

    public PersonResponseDTO findById(Integer id) {
        return PersonMapper.toResponseDTO(personRepository.findById(id).orElseThrow(EntityNotFoundException::new));
    }

    public PersonResponseDTO findByUsername(String username) {
        return PersonMapper.toResponseDTO(personRepository.findByUsername(username));
    }

    public PersonResponseDTO update(PersonRequestDTO personRequestDTO, Integer id) {
        Person existing = personRepository.findById(id).orElseThrow(EntityNotFoundException::new);

        mergePersons(existing, PersonMapper.fromRequestDTO(personRequestDTO));

        return PersonMapper.toResponseDTO(personRepository.save(existing));
    }

    public void deleteById(Integer id) {
        personRepository.deleteById(id);
    }

    private void mergePersons(Person existing, Person changing) {
        Map<String, List<String>> errors = new HashMap<>();
        if (changing.getItems() != null) {
            existing.setItems(changing.getItems());
        }

        if (changing.getUsername() != null) {
            if (StringUtils.isNotBlank(changing.getUsername())) {
                String newPassword = passwordEncoder.encode(changing.getPassword());
                existing.setPassword(newPassword);
            } else {
                errors.put("username", List.of("username must not be empty"));
            }
        }

        if (changing.getPassword() != null) {
            if (StringUtils.isBlank(changing.getPassword())) {
                errors.put("password", List.of("password must not be empty"));
            } else if (changing.getPassword().length() < 8) {
                errors.put("password", List.of("password must at least 8 chars"));
            } else {
                existing.setPassword(changing.getPassword());
            }
        }

        if (!errors.isEmpty()) {
            throw new FailedValidationException(errors);
        }


    }
}