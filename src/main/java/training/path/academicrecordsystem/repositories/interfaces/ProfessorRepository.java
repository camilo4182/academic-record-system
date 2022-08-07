package training.path.academicrecordsystem.repositories.interfaces;

import training.path.academicrecordsystem.model.Professor;

import java.util.List;
import java.util.Optional;

public interface ProfessorRepository {

    int save(Professor professor);
    int update(String id, Professor professor);
    int deleteById(String id);
    Optional<Professor> findById(String id);
    Optional<Professor> findByName(String name);
    List<Professor> findAll();
    boolean exists(String id);

}
