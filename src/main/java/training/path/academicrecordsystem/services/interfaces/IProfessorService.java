package training.path.academicrecordsystem.services.interfaces;

import training.path.academicrecordsystem.exceptions.NotFoundResourceException;
import training.path.academicrecordsystem.model.Professor;

import java.util.List;
import java.util.Optional;

public interface IProfessorService {

    void save(Professor professor);
    void update(String id, Professor professor) throws NotFoundResourceException;
    void deleteById(String id);
    Optional<Professor> findById(String id);
    Optional<Professor> findByName(String name);
    List<Professor> findAll();

}
