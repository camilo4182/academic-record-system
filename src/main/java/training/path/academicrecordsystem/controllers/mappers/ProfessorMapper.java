package training.path.academicrecordsystem.controllers.mappers;

import training.path.academicrecordsystem.controllers.dtos.ProfessorDTO;
import training.path.academicrecordsystem.exceptions.BadArgumentsException;
import training.path.academicrecordsystem.exceptions.NullRequestBodyException;
import training.path.academicrecordsystem.model.Professor;

import java.util.Objects;
import java.util.UUID;

public class ProfessorMapper {

    public static ProfessorDTO toDTO(Professor professor) {
        ProfessorDTO professorDTO = new ProfessorDTO();
        professorDTO.setId(professor.getId());
        professorDTO.setName(professor.getName());
        professorDTO.setEmail(professor.getEmail());
        professorDTO.setSalary(professor.getSalary());
        return professorDTO;
    }

    public static Professor toEntity(ProfessorDTO professorDTO) throws NullRequestBodyException, BadArgumentsException {
        validateProfessorDTO(professorDTO);
        Professor professor = new Professor();
        professor.setId(professorDTO.getId());
        professor.setName(professorDTO.getName());
        professor.setEmail(professorDTO.getEmail());
        professor.setSalary(professorDTO.getSalary());
        return professor;
    }

    public static Professor createEntity(ProfessorDTO professorDTO) throws NullRequestBodyException, BadArgumentsException {
        validateProfessorDTO(professorDTO);
        Professor professor = new Professor();
        professor.setId(UUID.randomUUID().toString());
        professor.setName(professorDTO.getName());
        professor.setEmail(professorDTO.getEmail());
        professor.setSalary(professorDTO.getSalary());
        return professor;
    }

    private static void validateProfessorDTO(ProfessorDTO professorDTO) throws NullRequestBodyException, BadArgumentsException {
        if (!Objects.nonNull(professorDTO)) throw new NullRequestBodyException("ProfessorDTO object is null");
        if (!Objects.nonNull(professorDTO.getName())) throw new BadArgumentsException("Name cannot be null");
        if (!Objects.nonNull(professorDTO.getEmail())) throw new BadArgumentsException("Email cannot be null");
        if (professorDTO.getName().isBlank()) throw new BadArgumentsException("Name cannot be empty");
        if (professorDTO.getEmail().isBlank()) throw new BadArgumentsException("Email cannot be empty");
        if (professorDTO.getSalary() < 0) throw new BadArgumentsException("Salary must be greater than zero");
    }

}
