package training.path.academicrecordsystem.services.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import training.path.academicrecordsystem.exceptions.ResourceNotFoundException;
import training.path.academicrecordsystem.exceptions.UniqueColumnViolationException;
import training.path.academicrecordsystem.model.Career;
import training.path.academicrecordsystem.model.Course;
import training.path.academicrecordsystem.repositories.interfaces.ICareerRepository;
import training.path.academicrecordsystem.repositories.interfaces.ICourseRepository;
import training.path.academicrecordsystem.services.interfaces.ICareerService;

import java.util.List;
import java.util.Optional;

@Service
@Validated
public class CareerService implements ICareerService {

    private final ICareerRepository careerRepository;
    private final ICourseRepository courseRepository;

    @Autowired
    public CareerService(ICareerRepository careerRepository, ICourseRepository courseRepository) {
        this.careerRepository = careerRepository;
        this.courseRepository = courseRepository;
    }

    @Override
    public void save(Career career) throws UniqueColumnViolationException {
        Optional<Career> foundCareer = careerRepository.findByName(career.getName());
        if (foundCareer.isPresent()) throw new UniqueColumnViolationException("There is already a career with the name " + career.getName() + ". Enter another one.");
        careerRepository.save(career);
    }

    @Override
    public void update(Career career) throws ResourceNotFoundException, UniqueColumnViolationException {
        if (!careerRepository.exists(career.getId())) throw new ResourceNotFoundException("Career " + career.getId() + " was not found");
        Optional<Career> foundCareer = careerRepository.findByName(career.getName());
        if (foundCareer.isPresent()) throw new UniqueColumnViolationException("There is already a career with the name " + career.getName() + ". Enter another one.");
        careerRepository.update(career.getId(), career);
    }

    @Override
    public void deleteById(String id) throws ResourceNotFoundException {
        if (!careerRepository.exists(id)) throw new ResourceNotFoundException("Career " + id + " was not found");
        careerRepository.deleteById(id);
    }

    @Override
    public Career findById(String id) throws ResourceNotFoundException {
        return careerRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Career with id " + id + " does not exist"));
    }

    @Override
    public List<Career> findAll() {
        return careerRepository.findAll();
    }

    @Override
    public List<Career> findAll(int limit, int offset) {
        return careerRepository.findAll(limit, offset);
    }

    @Override
    public void assignCourseToCareer(String courseId, String careerId) throws ResourceNotFoundException, UniqueColumnViolationException {
        if (!careerRepository.exists(careerId))
            throw new ResourceNotFoundException("Career with id " + careerId + " does not exist");
        if (!courseRepository.exists(courseId))
            throw new ResourceNotFoundException("Course with id " + courseId + " does not exist");
        if (careerRepository.isCourseAssignedToCareer(courseId, careerId))
            throw new UniqueColumnViolationException("This course is already assigned to this career");
        careerRepository.assignCourseToCareer(courseId, careerId);
    }

    @Override
    public List<Course> findCoursesByCareer(String careerId) throws ResourceNotFoundException {
        if (!careerRepository.exists(careerId)) throw new ResourceNotFoundException("Career with id " + careerId + " does not exist");
        return careerRepository.findCoursesByCareer(careerId);
    }

}
