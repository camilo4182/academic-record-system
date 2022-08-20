package training.path.academicrecordsystem.controllers.mappers;

import training.path.academicrecordsystem.controllers.dtos.ProfessorDTO;
import training.path.academicrecordsystem.model.Professor;

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
        Professor professor = new Professor();
        professor.setId(professorDTO.getId());
        professor.setName(professorDTO.getName());
        professor.setEmail(professorDTO.getEmail());
        professor.setSalary(professorDTO.getSalary());
        return professor;
    }

    public static Professor createEntity(ProfessorDTO professorDTO) {
        Professor professor = new Professor();
        professor.setId(UUID.randomUUID().toString());
        professor.setName(professorDTO.getName());
        professor.setEmail(professorDTO.getEmail());
        professor.setSalary(professorDTO.getSalary());
        return professor;
    }

}
