package training.path.academicrecordsystem.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import training.path.academicrecordsystem.model.User;
import training.path.academicrecordsystem.repositories.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class UserService implements IUserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User findById(long id) {
        return userRepository.findById(id).orElseThrow();
    }

    @Override
    public User findByName(String name) {
        return userRepository.findByName(name).orElseThrow();
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public void save(User user) throws Exception {
        int row = userRepository.save(user);
        if (row == 0) {
            throw new Exception("Could not create the user");
        }
    }

    @Override
    public void update(long id, User user) throws Exception {
        int modifiedRows = userRepository.update(id, user);
        if (modifiedRows == 0) {
            throw new Exception("Could not update the user");
        }
    }

    @Override
    public void deleteById(long id) throws Exception {
        int deletedRows = userRepository.deleteById(id);
        if (deletedRows == 0) {
            throw new Exception("User does not exist");
        }
    }

    @Override
    public void deleteAll() {
        userRepository.deleteAll();
    }
}
