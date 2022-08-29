package training.path.academicrecordsystem.services.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import training.path.academicrecordsystem.exceptions.ResourceNotFoundException;
import training.path.academicrecordsystem.exceptions.UniqueColumnViolationException;
import training.path.academicrecordsystem.model.CourseClass;
import training.path.academicrecordsystem.model.Professor;
import training.path.academicrecordsystem.model.Role;
import training.path.academicrecordsystem.repositories.interfaces.IProfessorRepository;
import training.path.academicrecordsystem.repositories.interfaces.IUserRepository;
import training.path.academicrecordsystem.services.interfaces.IProfessorService;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Validated
public class ProfessorService implements IProfessorService {

    private final IUserRepository userRepository;
    private final IProfessorRepository professorRepository;

    @Autowired
    public ProfessorService(IProfessorRepository professorRepository, IUserRepository userRepository) {
        this.userRepository = userRepository;
        this.professorRepository = professorRepository;
    }

    @Override
    public void save(Professor professor) throws UniqueColumnViolationException, ResourceNotFoundException {
        Role role = userRepository.findRoleByName(professor.getRole().getRoleName()).orElseThrow(
                () -> new ResourceNotFoundException("The role " + professor.getRole().getRoleName() + " is not available")
        );
        professor.setRole(role);
        verifyUniqueness(professor);
        professorRepository.save(professor);
    }

    @Override
    public void update(String id, Professor professor) throws ResourceNotFoundException, UniqueColumnViolationException {
        if (!professorRepository.exists(professor.getId())) throw new ResourceNotFoundException("Professor " + professor.getId() + " was not found");
        verifyUniqueness(professor);
        professorRepository.update(id, professor);
    }

    @Override
    public void updateBasicInfo(String id, Professor professor) throws ResourceNotFoundException, UniqueColumnViolationException {
        if (!professorRepository.exists(professor.getId())) throw new ResourceNotFoundException("Professor " + professor.getId() + " was not found");
        verifyUniqueness(professor);
        professorRepository.updateBasicInfo(id, professor);
    }

    private void verifyUniqueness(Professor professor) throws UniqueColumnViolationException {
        Optional<Professor> foundProfessorWithUserName = professorRepository.findByUserName(professor.getUserName());
        if (foundProfessorWithUserName.isPresent() && !Objects.equals(foundProfessorWithUserName.get().getId(), professor.getId()))
            throw new UniqueColumnViolationException("There is already a professor named " + professor.getFirstName() + " " + professor.getLastName() + ". Enter another one.");

        Optional<Professor> foundProfessorWithEmail = professorRepository.findByEmail(professor.getEmail());
        if (foundProfessorWithEmail.isPresent() && !Objects.equals(foundProfessorWithEmail.get().getId(), professor.getId()))
            throw new UniqueColumnViolationException("There is already a professor with the email " + professor.getEmail() + ". Enter another one.");
    }

    @Override
    public void deleteById(String id) throws ResourceNotFoundException {
        if (!professorRepository.exists(id)) throw new ResourceNotFoundException("Professor " + id + " was not found");
        professorRepository.deleteById(id);
    }

    @Override
    public Professor findById(String id) throws ResourceNotFoundException {
        return professorRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Professor " + id + " was not found"));
    }

    @Override
    public List<Professor> findAll() {
        return professorRepository.findAll();
    }

    @Override
    public List<Professor> findAll(int limit, int offset) {
        return professorRepository.findAll(limit, offset);
    }

    @Override
    public List<CourseClass> findClassesByProfessor(String id) throws ResourceNotFoundException {
        if (!professorRepository.exists(id)) throw new ResourceNotFoundException("Professor " + id + " was not found");
        return professorRepository.findClasses(id);
    }

}
