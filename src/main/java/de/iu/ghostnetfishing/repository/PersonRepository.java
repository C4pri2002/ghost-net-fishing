package de.iu.ghostnetfishing.repository;


import de.iu.ghostnetfishing.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Long> {
}
