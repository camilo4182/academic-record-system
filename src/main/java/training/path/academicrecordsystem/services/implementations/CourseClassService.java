package training.path.academicrecordsystem.services.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import training.path.academicrecordsystem.exceptions.ResourceNotFoundException;
import training.path.academicrecordsystem.model.CourseClass;
import training.path.academicrecordsystem.repositories.interfaces.CourseClassRepository;
import training.path.academicrecordsystem.repositories.interfaces.CourseRepository;
import training.path.academicrecordsystem.repositories.interfaces.ProfessorRepository;
import training.path.academicrecordsystem.services.interfaces.ICourseClassService;

import java.util.List;

@Service
@Validated
public class CourseClassService implements ICourseClassService {

    private final CourseClassRepository courseClassRepository;
    private final CourseRepository courseRepository;
    private final ProfessorRepository professorRepository;

    @Autowired
    public CourseClassService(CourseClassRepository courseClassRepository, CourseRepository courseRepository, ProfessorRepository professorRepository) {
        this.courseClassRepository = courseClassRepository;
        this.courseRepository = courseRepository;
        this.professorRepository = professorRepository;
    }

    @Override
    public void save(CourseClass courseClass) throws ResourceNotFoundException {
        if (!courseRepository.exists(courseClass.getCourse().getId())) throw new ResourceNotFoundException("Course " + courseClass.getCourse().getId() + " was not found");
        if (!professorRepository.exists(courseClass.getProfessor().getId())) throw new ResourceNotFoundException("Professor " + courseClass.getProfessor().getId() + " was not found");
        courseClassRepository.save(courseClass);
    }

    @Override
    public void update(CourseClass courseClass) throws ResourceNotFoundException {
        if (!courseClassRepository.exists(courseClass.getId())) throw new ResourceNotFoundException("Class " + courseClass.getId() + " was not found");
        if (!courseRepository.exists(courseClass.getCourse().getId())) throw new ResourceNotFoundException("Course " + courseClass.getCourse().getId() + " was not found");
        if (!professorRepository.exists(courseClass.getProfessor().getId())) throw new ResourceNotFoundException("Professor " + courseClass.getProfessor().getId() + " was not found");
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
}
