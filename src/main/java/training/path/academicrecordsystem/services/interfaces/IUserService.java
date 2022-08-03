package training.path.academicrecordsystem.services.interfaces;

import training.path.academicrecordsystem.exceptions.NoUsersException;
import training.path.academicrecordsystem.model.User;

import java.util.List;

public interface IUserService {

    User findById(long id);
    User findByName(String name);
    List<User> findAll() throws NoUsersException;
    int save(User user) throws Exception;
    int update(long id, User user) throws Exception;
    int deleteById(long id) throws Exception;
    void deleteAll() throws Exception;

}
