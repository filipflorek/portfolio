package com.florek.NBA_backend.repository;

import com.florek.NBA_backend.model.people.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;


public interface PersonRepository extends JpaRepository<Person, Integer> {


    @Query(value = "SELECT COUNT(*) FROM Person WHERE email = ?1", nativeQuery = true)
    int countByEmail(String email);

    @Query(value = "SELECT COUNT(*) FROM Person WHERE login = ?1", nativeQuery = true)
    int countByLogin(String login);

    @Query(value = "SELECT * FROM Person WHERE login = ?1 AND password = ?2", nativeQuery = true)
    Person getPersonByCredentials(String login, String password);

    @Query(value = "SELECT password FROM Person WHERE login = ?1", nativeQuery = true)
    String getUserHashedPassword(String login);

    @Query(value = "SELECT * FROM Person WHERE login = ?1", nativeQuery = true)
    Optional<Person> getPersonByLogin(String login);

}
