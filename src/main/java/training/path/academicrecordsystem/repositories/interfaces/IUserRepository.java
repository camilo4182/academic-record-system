package training.path.academicrecordsystem.repositories.interfaces;

import training.path.academicrecordsystem.model.User;

import java.util.Optional;

public interface IUserRepository {

    Optional<User> findByName(String name);

}
