package ee.geir.marketplace.repository;

import ee.geir.marketplace.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {

    Person findByEmailIgnoreCase(String email);
    Person findByDisplayNameIgnoreCase(String displayName);
}
