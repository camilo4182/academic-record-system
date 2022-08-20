package training.path.academicrecordsystem.controllers.mappers;

import training.path.academicrecordsystem.controllers.dtos.ProfessorDTO;
import training.path.academicrecordsystem.exceptions.BadResourceDataException;
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

    public static Professor toEntity(ProfessorDTO professorDTO) {
        //validateProfessorDTO(professorDTO);
        Professor professor = new Professor();
        professor.setId(professorDTO.getId());
        professor.setName(professorDTO.getName());
        professor.setEmail(professorDTO.getEmail());
        professor.setSalary(professorDTO.getSalary());
        return professor;
    }

    public static Professor createEntity(ProfessorDTO professorDTO) {
        //validateProfessorDTO(professorDTO);
        Professor professor = new Professor();
        professor.setId(UUID.randomUUID().toString());
        professor.setName(professorDTO.getName());
        professor.setEmail(professorDTO.getEmail());
        professor.setSalary(professorDTO.getSalary());
        return professor;
    }

    private static void validateProfessorDTO(ProfessorDTO professorDTO) throws NullRequestBodyException, BadResourceDataException {
        if (Objects.isNull(professorDTO)) throw new NullRequestBodyException("You must provide professor information");
        if (Objects.isNull(professorDTO.getName())) throw new BadResourceDataException("Professor name cannot be bull");
        if (Objects.isNull(professorDTO.getEmail())) throw new BadResourceDataException("Email cannot be null");
        if (professorDTO.getName().isEmpty()) throw new BadResourceDataException("You must provide the professor name");
        if (professorDTO.getName().isBlank()) throw new BadResourceDataException("Professor name cannot be just blank spaces");
        if (professorDTO.getEmail().isEmpty()) throw new BadResourceDataException("You must provide the professor email");
        if (professorDTO.getEmail().isBlank()) throw new BadResourceDataException("Professor email cannot be just blank spaces");
        if (professorDTO.getSalary() < 0) throw new BadResourceDataException("Professor salary must be greater than zero");
    }

}
