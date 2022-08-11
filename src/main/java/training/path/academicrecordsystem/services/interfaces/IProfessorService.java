package training.path.academicrecordsystem.services.interfaces;

import training.path.academicrecordsystem.exceptions.NotFoundResourceException;
import training.path.academicrecordsystem.model.Professor;

import java.util.List;

public interface IProfessorService {

    void save(Professor professor);
    void update(String id, Professor professor) throws NotFoundResourceException;
    void deleteById(String id) throws NotFoundResourceException;
    Professor findById(String id) throws NotFoundResourceException;
    Professor findByName(String name) throws NotFoundResourceException;
    List<Professor> findAll();
    List<Professor> findAll(int limit, int offset);


}
