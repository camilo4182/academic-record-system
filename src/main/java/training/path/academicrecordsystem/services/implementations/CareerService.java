package training.path.academicrecordsystem.services.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import training.path.academicrecordsystem.exceptions.NotFoundResourceException;
import training.path.academicrecordsystem.model.Career;
import training.path.academicrecordsystem.model.Course;
import training.path.academicrecordsystem.model.CourseClass;
import training.path.academicrecordsystem.repositories.interfaces.CareerRepository;
import training.path.academicrecordsystem.services.interfaces.ICareerService;

import java.util.List;

@Service
public class CareerService implements ICareerService {

    private final CareerRepository careerRepository;

    @Autowired
    public CareerService(CareerRepository careerRepository) {
        this.careerRepository = careerRepository;
    }

    @Override
    public void save(Career career) {
        careerRepository.save(career);
    }

    @Override
    public void update(Career career) throws NotFoundResourceException {
        if (!careerRepository.exists(career.getId())) throw new NotFoundResourceException("Career " + career.getId() + " was not found");
        careerRepository.update(career.getId(), career);
    }

    @Override
    public void deleteById(String id) throws NotFoundResourceException {
        if (!careerRepository.exists(id)) throw new NotFoundResourceException("Career " + id + " was not found");
        careerRepository.deleteById(id);
    }

    @Override
    public Career findById(String id) throws NotFoundResourceException {
        return careerRepository.findById(id).orElseThrow(() -> new NotFoundResourceException("Career with id " + id + " does not exist"));
    }

    @Override
    public Career findByName(String name) throws NotFoundResourceException {
        return careerRepository.findByName(name).orElseThrow(() -> new NotFoundResourceException("Career with  " + name + " does not exist"));
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
    public void assignClassesToCareer(String careerId, List<CourseClass> classes) throws NotFoundResourceException {
        if (!careerRepository.exists(careerId)) throw new NotFoundResourceException("Career with id " + careerId + " does not exist");
        for (CourseClass courseClass : classes) {
            careerRepository.insertIntoCareerClasses(careerId, courseClass.getId());
        }
    }

    @Override
    public List<Course> findCoursesByCareer(String careerId) throws NotFoundResourceException {
        if (!careerRepository.exists(careerId)) throw new NotFoundResourceException("Career with id " + careerId + " does not exist");
        return careerRepository.getCoursesByCareer(careerId);
    }

}
