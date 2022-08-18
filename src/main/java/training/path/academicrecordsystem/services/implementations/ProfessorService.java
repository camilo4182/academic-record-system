package training.path.academicrecordsystem.services.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import training.path.academicrecordsystem.exceptions.ResourceNotFoundException;
import training.path.academicrecordsystem.model.Professor;
import training.path.academicrecordsystem.repositories.interfaces.ProfessorRepository;
import training.path.academicrecordsystem.services.interfaces.IProfessorService;

import java.util.List;

@Service
@Validated
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
    public void update(String id, Professor professor) throws ResourceNotFoundException {
        if (!professorRepository.exists(professor.getId())) throw new ResourceNotFoundException("Professor " + professor.getId() + " was not found");
        professorRepository.update(id, professor);
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
}
