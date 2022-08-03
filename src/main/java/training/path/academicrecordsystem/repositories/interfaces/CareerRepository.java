package training.path.academicrecordsystem.repositories.interfaces;

import training.path.academicrecordsystem.model.Career;

import java.util.List;
import java.util.Optional;

public interface CareerRepository {

    Optional<Career> findById(long id);
    Optional<Career> findByName(String name);
    List<Career> findAll();
    int save(Career career);
    int update(long id, Career career);
    int deleteById(long id);
    
}
