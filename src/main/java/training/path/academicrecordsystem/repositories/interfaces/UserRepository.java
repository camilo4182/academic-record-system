package training.path.academicrecordsystem.repositories.interfaces;

import training.path.academicrecordsystem.model.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {

    Optional<User> findById(long id);
    Optional<User> findByName(String name);
    List<User> findAll();
    int save(User user);
    int update(long id, User user);
    int deleteById(long id);
    int deleteAll();

}
