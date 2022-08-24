package training.path.academicrecordsystem.repositories.interfaces;

import training.path.academicrecordsystem.model.Professor;

import java.util.List;
import java.util.Optional;

public interface IProfessorRepository {

    int save(Professor professor);

    int update(String id, Professor professor);

    int deleteById(String id);

    Optional<Professor> findById(String id);

    Optional<Professor> findByUserName(String name);

    Optional<Professor> findByEmail(String email);

    List<Professor> findAll();

    List<Professor> findAll(int limit, int offset);

    boolean exists(String id);

}
