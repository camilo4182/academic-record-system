package training.path.academicrecordsystem.services;

import training.path.academicrecordsystem.model.User;

import java.util.List;

public interface IUserService {

    User findById(long id);
    User findByName(String name);
    List<User> findAll();
    void save(User user) throws Exception;
    void update(long id, User user) throws Exception;
    void deleteById(long id) throws Exception;
    void deleteAll() throws Exception;

}
