package training.path.academicrecordsystem.repositories.interfaces;

import training.path.academicrecordsystem.model.Role;
import training.path.academicrecordsystem.model.User;

import java.util.Optional;

public interface IUserRepository {

    int update(String id, User user);

    int deleteById(String id);

    Optional<User> findByUserName(String userName);

    Optional<User> findByEmail(String email);

    Optional<Role> findRoleByName(String name);

    boolean exists(String id);

}
