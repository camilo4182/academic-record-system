package training.path.academicrecordsystem.services.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import training.path.academicrecordsystem.exceptions.ResourceNotFoundException;
import training.path.academicrecordsystem.exceptions.UniqueColumnViolationException;
import training.path.academicrecordsystem.model.CourseClass;
import training.path.academicrecordsystem.repositories.interfaces.ICourseClassRepository;
import training.path.academicrecordsystem.repositories.interfaces.ICourseRepository;
import training.path.academicrecordsystem.repositories.interfaces.IProfessorRepository;
import training.path.academicrecordsystem.repositories.interfaces.IUserRepository;
import training.path.academicrecordsystem.services.interfaces.ICourseClassService;

import java.util.List;

@Service
@Validated
public class CourseClassService implements ICourseClassService {

    private final ICourseClassRepository courseClassRepository;
    private final ICourseRepository courseRepository;
    private final IUserRepository userRepository;

    @Autowired
    public CourseClassService(ICourseClassRepository courseClassRepository, ICourseRepository courseRepository, IUserRepository userRepository) {
        this.courseClassRepository = courseClassRepository;
        this.courseRepository = courseRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void save(CourseClass courseClass) throws ResourceNotFoundException, UniqueColumnViolationException {
        if (!courseRepository.exists(courseClass.getCourse().getId()))
            throw new ResourceNotFoundException("Course " + courseClass.getCourse().getId() + " was not found");
        if (!userRepository.exists(courseClass.getProfessor().getId()))
            throw new ResourceNotFoundException("Professor " + courseClass.getProfessor().getId() + " was not found");
        if (courseClassRepository.exists(courseClass.getProfessor().getId(), courseClass.getCourse().getId()))
            throw new UniqueColumnViolationException("This professor is already teaching in this class");
        courseClassRepository.save(courseClass);
    }

    @Override
    public void update(CourseClass courseClass) throws ResourceNotFoundException {
        if (!courseClassRepository.exists(courseClass.getId())) throw new ResourceNotFoundException("Class " + courseClass.getId() + " was not found");
        if (!courseRepository.exists(courseClass.getCourse().getId())) throw new ResourceNotFoundException("Course " + courseClass.getCourse().getId() + " was not found");
        if (!userRepository.exists(courseClass.getProfessor().getId())) throw new ResourceNotFoundException("Professor " + courseClass.getProfessor().getId() + " was not found");
        courseClassRepository.update(courseClass.getId(), courseClass);
    }

    @Override
    public void deleteById(String id) throws ResourceNotFoundException {
        if (!courseClassRepository.exists(id)) throw new ResourceNotFoundException("Class with id " + id + " was not found");
        courseClassRepository.deleteById(id);
    }

    @Override
    public CourseClass findById(String id) throws ResourceNotFoundException {
        return courseClassRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Class with id " + id + " was not found"));
    }

    @Override
    public List<CourseClass> findAll() {
        return courseClassRepository.findAll();
    }

    @Override
    public List<CourseClass> findAll(int limit, int offset) {
        return courseClassRepository.findAll(limit, offset);
    }

    @Override
    public List<CourseClass> findAllAvailable() {
        return courseClassRepository.findAllAvailable();
    }

    @Override
    public List<CourseClass> findAllAvailable(int limit, int offset) {
        return courseClassRepository.findAllAvailable(limit, offset);
    }

    @Override
    public List<CourseClass> findAllUnavailable() {
        return courseClassRepository.findAllUnavailable();
    }

    @Override
    public List<CourseClass> findAllUnavailable(int limit, int offset) {
        return courseClassRepository.findAllUnavailable(limit, offset);
    }

}
