package training.path.academicrecordsystem.services.interfaces;

import training.path.academicrecordsystem.exceptions.ResourceNotFoundException;
import training.path.academicrecordsystem.model.Professor;
import training.path.academicrecordsystem.validations.custom.UUIDValidator;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;

public interface IProfessorService {

    void save(@Valid Professor professor);

    void update(@UUIDValidator String id, @Valid Professor professor) throws ResourceNotFoundException;

    void deleteById(@UUIDValidator String id) throws ResourceNotFoundException;

    Professor findById(@UUIDValidator String id) throws ResourceNotFoundException;

    List<Professor> findAll();

    List<Professor> findAll(@Min(0) int limit, @Min(0) int offset);

}
