package ee.geir.marketplace.controller;

import ee.geir.marketplace.entity.Person;
import ee.geir.marketplace.repository.PersonRepository;
import ee.geir.marketplace.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
public class PersonController {

    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private PersonService personService;

    @GetMapping("persons")
    public List<Person> findAll() {
        return personRepository.findAll();
    }

    @PostMapping("signup")
    public Person signup(@RequestBody Person person) {
        personService.validate(person);
        return personRepository.save(person);
    }

    @PatchMapping("persons/{id}/profile-picture")
    public Person uploadProfilePicture(@PathVariable Long id, @RequestParam("file") MultipartFile file) throws IOException {
        return personService.updateProfilePicture(id, file);
    }
}
