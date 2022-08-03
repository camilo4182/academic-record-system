package training.path.academicrecordsystem.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import training.path.academicrecordsystem.exceptions.AlreadyExistingUserNameException;
import training.path.academicrecordsystem.exceptions.EmptyUserNameException;
import training.path.academicrecordsystem.exceptions.NoUsersException;
import training.path.academicrecordsystem.model.User;
import training.path.academicrecordsystem.repositories.UserRepository;

import java.util.List;
import java.util.NoSuchElementException;

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
    public List<User> findAll() throws NoUsersException {
        List<User> users = userRepository.findAll();
        if (users.isEmpty()) throw new NoUsersException();
        return users;
    }

    @Override
    public int save(User user) throws EmptyUserNameException, AlreadyExistingUserNameException {
        if (user.getName().isEmpty()) throw new EmptyUserNameException();
        if (userRepository.findByName(user.getName()).isPresent()) throw new AlreadyExistingUserNameException(user.getName());
        return userRepository.save(user);
    }

    @Override
    public int update(long id, User user) throws EmptyUserNameException {
        if (user.getName().isEmpty()) throw new EmptyUserNameException();
        if (userRepository.findById(id).isEmpty()) throw new NoSuchElementException();
        return userRepository.update(id, user);
    }

    @Override
    public int deleteById(long id) throws NoSuchElementException {
        if (userRepository.findById(id).isEmpty()) throw new NoSuchElementException();
        return userRepository.deleteById(id);
    }

    @Override
    public void deleteAll() throws Exception {
        if (userRepository.findAll().isEmpty()) throw new Exception("No users available");
        int rowsDeleted = userRepository.deleteAll();
        if (rowsDeleted == 0) throw new Exception("Cannot delete users that are assigned to a course");
    }
}
