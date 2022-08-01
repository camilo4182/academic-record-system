package training.path.academicrecordsystem.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import training.path.academicrecordsystem.model.Course;
import training.path.academicrecordsystem.repositories.JdbcCourseRepository;

import java.util.List;

@Service
public class CourseService implements ICourseService {

    private JdbcCourseRepository jdbcCourseRepository;

    @Autowired
    public CourseService(JdbcCourseRepository jdbcCourseRepository) {
        this.jdbcCourseRepository = jdbcCourseRepository;
    }

    @Override
    public Course findById(long id) {
        return jdbcCourseRepository.findById(id).orElseThrow();
    }

    @Override
    public Course findByName(String name) {
        return jdbcCourseRepository.findByName(name).orElseThrow();
    }

    @Override
    public List<Course> findCoursesByUser(long userId) {
        return jdbcCourseRepository.findCoursesByUser(userId);
    }

    @Override
    public List<Course> findAll() {
        return jdbcCourseRepository.findAll();
    }

    @Override
    public void save(Course course) throws Exception {
        int row = jdbcCourseRepository.save(course);
        if (row == 0) {
            throw new Exception("Could not update the user");
        }
    }

    @Override
    public void update(long id, Course course) throws Exception {
        int modifiedRows = jdbcCourseRepository.update(id, course);
        if (modifiedRows == 0) {
            throw new Exception("Could not update the user");
        }
    }

    @Override
    public void deleteById(long id) throws Exception {
        int deletedRows = jdbcCourseRepository.deleteById(id);
        if (deletedRows == 0) {
            throw new Exception("User does not exist");
        }
    }

    @Override
    public void deleteAll() {
        jdbcCourseRepository.deleteAll();
    }
}
