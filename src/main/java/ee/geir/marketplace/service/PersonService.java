package ee.geir.marketplace.service;

import ee.geir.marketplace.entity.Person;
import ee.geir.marketplace.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import util.Validations;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Set;

@Service
public class PersonService {

    @Autowired
    private PersonRepository personRepository;

    private static final Set<String> ALLOWED_MIME_TYPES = Set.of(
            "image/jpeg",
            "image/png",
            "image/webp"
    );

    private static final Set<String> ALLOWED_EXTENSIONS = Set.of(
            "jpg",
            "jpeg",
            "png",
            "webp"
    );

    public void validate(Person person) {
        if (person.getId() != null) {
            throw new RuntimeException("Cannot sign up with ID");
        }
        if (person.getEmail() == null) {
            throw new RuntimeException("Cannot sign up without email");
        }
        if (!Validations.validateEmail(person.getEmail())){
            throw new RuntimeException("Invalid email");
        }
        Person dbPerson = personRepository.findByEmailIgnoreCase(person.getEmail());
        if (dbPerson != null) {
            throw new RuntimeException("Email is already in use");
        }
        if (person.getPassword() == null || person.getPassword().isBlank()) {
            throw new RuntimeException("Cannot sign up without password");
        }
        if (!Validations.validatePassword(person.getPassword())){
            throw new RuntimeException("Password must be at least 12 characters, include a small and a big letter, a number, and a special character.");
        }
        if (person.getDisplayName() == null || person.getDisplayName().isBlank()) {
            throw new RuntimeException("Cannot sign up without a display name");
        }
        Person dbPersonWithDisplayName = personRepository.findByDisplayNameIgnoreCase(person.getDisplayName());
        if (dbPersonWithDisplayName != null) {
            throw new RuntimeException("Display name is already in use");
        }
    }

    public Person updateProfilePicture(Long personId, MultipartFile file) throws IOException {
        validateImage(file);

        Person person = personRepository.findById(personId)
                .orElseThrow(() -> new RuntimeException("Person not found"));

        // Delete old image
        if (person.getProfilePicturePath() != null && person.getProfilePicturePath().startsWith("/uploads/")) {
            Path oldFile = Paths.get("uploads")
                    .resolve(person.getProfilePicturePath().substring("/uploads/".length()));
            Files.deleteIfExists(oldFile);
        }

        String extension = StringUtils.getFilenameExtension(file.getOriginalFilename()).toLowerCase();
        String fileName = "profile_" + personId + "." + extension;

        Path uploadPath = Paths.get("uploads/profile");
        Files.createDirectories(uploadPath);

        Path filePath = uploadPath.resolve(fileName);
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        person.setProfilePicturePath("/uploads/profile/" + fileName);
        return personRepository.save(person);
    }

    public void validateImage(MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            throw new RuntimeException("File is empty");
        }

        String contentType = file.getContentType();
        if (contentType == null || !ALLOWED_MIME_TYPES.contains(contentType)) {
            throw new RuntimeException("Only JPG, JPEG, PNG or WEBP images are allowed");
        }

        String extension = StringUtils.getFilenameExtension(file.getOriginalFilename());
        if (extension == null || !ALLOWED_EXTENSIONS.contains(extension.toLowerCase())) {
            throw new RuntimeException("Invalid image extension");
        }

        if (!contentType.equals("image/webp")) {
            if (ImageIO.read(file.getInputStream()) == null) {
                throw new RuntimeException("Invalid image file");
            }
        }
    }


}
