package training.path.academicrecordsystem.services.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import training.path.academicrecordsystem.exceptions.NotFoundResourceException;
import training.path.academicrecordsystem.model.Professor;
import training.path.academicrecordsystem.repositories.implementations.JdbcProfessorRepository;
import training.path.academicrecordsystem.repositories.interfaces.ProfessorRepository;
import training.path.academicrecordsystem.services.interfaces.IProfessorService;

import java.util.List;

@Service
public class ProfessorService implements IProfessorService {

    private final JdbcProfessorRepository professorRepository;

    @Autowired
    public ProfessorService(JdbcProfessorRepository professorRepository) {
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
    public void deleteById(String id) throws NotFoundResourceException {
        if (!professorRepository.exists(id)) throw new NotFoundResourceException("Career " + id + " was not found");
        professorRepository.deleteById(id);
    }

    @Override
    public Professor findById(String id) throws NotFoundResourceException {
        return professorRepository.findById(id).orElseThrow(() -> new NotFoundResourceException("Professor " + id + " was not found"));
    }

    @Override
    public Professor findByName(String name) throws NotFoundResourceException {
        return professorRepository.findByName(name).orElseThrow(() -> new NotFoundResourceException("Course " + name + " was not found"));
    }

    @Override
    public List<Professor> findAll() {
        return professorRepository.findAll();
    }
}
