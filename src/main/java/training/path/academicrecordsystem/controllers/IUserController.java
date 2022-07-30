package training.path.academicrecordsystem.controllers;

import org.springframework.http.ResponseEntity;
import training.path.academicrecordsystem.model.User;

import java.util.List;

public interface IUserController {

    ResponseEntity<User> findById(long id);
    //ResponseEntity<User> findByName(String name);
    ResponseEntity<List<User>> findAll(String name);
    ResponseEntity<String> save(User user);
    ResponseEntity<String> update(long id, User user);
    ResponseEntity<String> deleteById(long id);
    ResponseEntity<String> deleteAll();

}
