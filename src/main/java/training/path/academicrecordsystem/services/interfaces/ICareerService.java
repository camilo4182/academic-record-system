package training.path.academicrecordsystem.services.interfaces;

import training.path.academicrecordsystem.exceptions.NotFoundResourceException;
import training.path.academicrecordsystem.model.Career;

import java.util.List;

public interface ICareerService {

    void save(Career career);
    void update(Career career) throws NotFoundResourceException;
    void deleteById(String id) throws NotFoundResourceException;
    Career findById(String id) throws NotFoundResourceException;
    Career findByName(String name) throws NotFoundResourceException;
    List<Career> findAll();

}
