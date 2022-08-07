package training.path.academicrecordsystem.services.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import training.path.academicrecordsystem.exceptions.NotFoundResourceException;
import training.path.academicrecordsystem.model.CourseClass;
import training.path.academicrecordsystem.repositories.interfaces.CourseClassRepository;
import training.path.academicrecordsystem.services.interfaces.ICourseClassService;

import java.util.List;

@Service
public class CourseClassService implements ICourseClassService {

    private final CourseClassRepository courseClassRepository;

    @Autowired
    public CourseClassService(CourseClassRepository courseClassRepository) {
        this.courseClassRepository = courseClassRepository;
    }

    @Override
    public void save(CourseClass courseClass) {
        courseClassRepository.save(courseClass);
    }

    @Override
    public void update(CourseClass courseClass) throws NotFoundResourceException {
        if (!courseClassRepository.exists(courseClass.getId())) throw new NotFoundResourceException("Class " + courseClass.getId() + " was not found");
        courseClassRepository.update(courseClass.getId(), courseClass);
    }

    @Override
    public void deleteById(String id) throws NotFoundResourceException {
        if (!courseClassRepository.exists(id)) throw new NotFoundResourceException("Class does not exist");
        courseClassRepository.deleteById(id);
    }

    @Override
    public CourseClass findById(String id) throws NotFoundResourceException {
        return courseClassRepository.findById(id).orElseThrow(() -> new NotFoundResourceException("Class with id " + id + " was not found"));
    }

    @Override
    public List<CourseClass> findAll() {
        return courseClassRepository.findAll();
    }
}
