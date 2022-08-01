package training.path.academicrecordsystem.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import training.path.academicrecordsystem.model.Course;
import training.path.academicrecordsystem.model.User;
import training.path.academicrecordsystem.repositories.JdbcCourseRepository;
import training.path.academicrecordsystem.repositories.JdbcUserRepository;

import java.util.List;

@Service
public class CourseService implements ICourseService {

    private final JdbcCourseRepository jdbcCourseRepository;
    private final JdbcUserRepository jdbcUserRepository;

    @Autowired
    public CourseService(JdbcCourseRepository jdbcCourseRepository, JdbcUserRepository jdbcUserRepository) {
        this.jdbcCourseRepository = jdbcCourseRepository;
        this.jdbcUserRepository = jdbcUserRepository;
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
    public void save(long userId, Course course) throws Exception {
        User user = jdbcUserRepository.findById(userId).orElseThrow();
        course.setCreatedBy(user);
        int row = jdbcCourseRepository.save(course);
        if (row == 0) {
            throw new Exception("Could not register the course");
        }
    }

    @Override
    public void update(long courseId, Course course) throws Exception {
        int modifiedRows = jdbcCourseRepository.update(courseId, course);
        if (modifiedRows == 0) {
            throw new Exception("Could not update the course information");
        }
    }

    @Override
    public void deleteById(long id) throws Exception {
        int deletedRows = jdbcCourseRepository.deleteById(id);
        if (deletedRows == 0) {
            throw new Exception("Course does not exist");
        }
    }

    @Override
    public void deleteCoursesByUser(long userId) throws Exception {
        int deletedRows = jdbcCourseRepository.deleteByUser(userId);
        if (deletedRows == 0) {
            throw new Exception("User courses could not be deleted");
        }
    }

    @Override
    public void deleteAll() {
        jdbcCourseRepository.deleteAll();
    }
}
