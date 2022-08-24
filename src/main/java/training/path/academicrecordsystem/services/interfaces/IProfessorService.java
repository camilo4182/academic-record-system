package training.path.academicrecordsystem.services.interfaces;

import org.springframework.validation.annotation.Validated;
import training.path.academicrecordsystem.exceptions.ResourceNotFoundException;
import training.path.academicrecordsystem.exceptions.UniqueColumnViolationException;
import training.path.academicrecordsystem.model.Professor;
import training.path.academicrecordsystem.validations.custom.UUIDValidator;
import training.path.academicrecordsystem.validations.groups.OnCreate;
import training.path.academicrecordsystem.validations.groups.OnUpdate;
import training.path.academicrecordsystem.validations.groups.OnUpdateByUser;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;

public interface IProfessorService {

    @Validated(OnCreate.class)
    void save(@Valid Professor professor) throws UniqueColumnViolationException;

    @Validated(OnUpdate.class)
    void update(@UUIDValidator String id, @Valid Professor professor) throws ResourceNotFoundException, UniqueColumnViolationException;

    @Validated(OnUpdateByUser.class)
    void updateBasicInfo(@UUIDValidator String id, @Valid Professor professor) throws ResourceNotFoundException, UniqueColumnViolationException;

    void deleteById(@UUIDValidator String id) throws ResourceNotFoundException;

    Professor findById(@UUIDValidator String id) throws ResourceNotFoundException;

    List<Professor> findAll();

    List<Professor> findAll(@Min(0) int limit, @Min(0) int offset);

}
