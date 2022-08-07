package training.path.academicrecordsystem.services.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import training.path.academicrecordsystem.exceptions.NotFoundResourceException;
import training.path.academicrecordsystem.model.Professor;
import training.path.academicrecordsystem.repositories.interfaces.ProfessorRepository;
import training.path.academicrecordsystem.services.interfaces.IProfessorService;

import java.util.List;
import java.util.Optional;

@Service
public class ProfessorService implements IProfessorService {

    private final ProfessorRepository professorRepository;

    @Autowired
    public ProfessorService(ProfessorRepository professorRepository) {
        this.professorRepository = professorRepository;
    }

    @Override
    public void save(Professor professor) {
        professorRepository.save(professor);
    }

    @Override
    public void update(String id, Professor professor) throws NotFoundResourceException {
        if (!professorRepository.exists(professor.getId())) throw new NotFoundResourceException("Professor " + professor.getName() + " was not found");
        professorRepository.update(id, professor);
    }

    @Override
    public void deleteById(String id) {

    }

    @Override
    public Optional<Professor> findById(String id) {
        return Optional.empty();
    }

    @Override
    public Optional<Professor> findByName(String name) {
        return Optional.empty();
    }

    @Override
    public List<Professor> findAll() {
        return null;
    }
}
