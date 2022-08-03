package training.path.academicrecordsystem.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import training.path.academicrecordsystem.exceptions.NoUsersException;
import training.path.academicrecordsystem.model.User;
import training.path.academicrecordsystem.services.interfaces.IUserService;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("users")
public class UserController implements IUserController {

    private final IUserService userService;

    @Autowired
    public UserController(IUserService userService) {
        this.userService = userService;
    }

    @Override
    @GetMapping("{id}")
    public ResponseEntity<User> findById(@PathVariable("id") long id) {
        try {
            User user = userService.findById(id);
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    @GetMapping
    public ResponseEntity<List<User>> findAll() {
        try {
            List<User> users = userService.findAll();
            return new ResponseEntity<>(users, HttpStatus.OK);
        } catch (NoUsersException e) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @Override
    @PostMapping
    public ResponseEntity<String> save(@RequestBody User user) {
        try {
            userService.save(user);
            return new ResponseEntity<>("User was created successfully", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    @PutMapping("{id}")
    public ResponseEntity<String> update(@PathVariable("id") long id, @RequestBody User user) {
        try {
            userService.update(id, user);
            return new ResponseEntity<>("User with id " + id + " was successfully update", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @Override
    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteById(@PathVariable("id") long id) {
        try {
            userService.deleteById(id);
            return new ResponseEntity<>("User with id " + id + " was successfully deleted", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Invalid user to delete", HttpStatus.NOT_FOUND);
        }
    }

    @Override
    @DeleteMapping
    public ResponseEntity<String> deleteAll() {
        try {
            userService.deleteAll();
            return new ResponseEntity<>("All users were deleted", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NO_CONTENT);
        }
    }

}