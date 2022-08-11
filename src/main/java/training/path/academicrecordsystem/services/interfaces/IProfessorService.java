package training.path.academicrecordsystem.services.interfaces;

import training.path.academicrecordsystem.exceptions.ResourceNotFoundException;
import training.path.academicrecordsystem.model.Professor;

import java.util.List;

public interface IProfessorService {

    void save(Professor professor);
    void update(String id, Professor professor) throws ResourceNotFoundException;
    void deleteById(String id) throws ResourceNotFoundException;
    Professor findById(String id) throws ResourceNotFoundException;
    Professor findByName(String name) throws ResourceNotFoundException;
    List<Professor> findAll();
    List<Professor> findAll(int limit, int offset);


}
