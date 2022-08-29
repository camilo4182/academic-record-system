package training.path.academicrecordsystem.controllers.mappers;

import training.path.academicrecordsystem.controllers.dtos.RequestBodyProfessorDTO;
import training.path.academicrecordsystem.controllers.dtos.ResponseBodyProfessorDTO;
import training.path.academicrecordsystem.controllers.dtos.UpdateProfessorByAdminDTO;
import training.path.academicrecordsystem.controllers.dtos.UpdateUserByUserDTO;
import training.path.academicrecordsystem.model.Professor;
import training.path.academicrecordsystem.model.Role;
import training.path.academicrecordsystem.security.interfaces.IRoles;

import java.util.UUID;

public class ProfessorMapper {

    public static ResponseBodyProfessorDTO toDTO(Professor professor) {
        ResponseBodyProfessorDTO professorDTO = new ResponseBodyProfessorDTO();
        professorDTO.setId(professor.getId());
        professorDTO.setFirstName(professor.getFirstName());
        professorDTO.setLastName(professor.getLastName());
        professorDTO.setUserName(professor.getUserName());
        professorDTO.setEmail(professor.getEmail());
        return professorDTO;
    }

    public static Professor toEntity(UpdateProfessorByAdminDTO professorDTO) {
        Professor professor = new Professor();
        professor.setId(professorDTO.getId());
        professor.setSalary(professorDTO.getSalary());
        return professor;
    }

    public static Professor toEntity(UpdateUserByUserDTO professorDTO) {
        Professor professor = new Professor();
        professor.setId(professorDTO.getId());
        professor.setFirstName(professorDTO.getFirstName());
        professor.setLastName(professorDTO.getLastName());
        professor.setUserName(professorDTO.getFirstName().toLowerCase() + "." + professorDTO.getLastName());
        professor.setPassword(professorDTO.getPassword());
        professor.setEmail(professorDTO.getEmail());
        return professor;
    }

    public static Professor createEntity(RequestBodyProfessorDTO professorDTO) {
        Professor professor = new Professor();
        professor.setId(UUID.randomUUID().toString());
        professor.setFirstName(professorDTO.getFirstName());
        professor.setLastName(professorDTO.getLastName());
        professor.setUserName(professorDTO.getFirstName().toLowerCase() + "." + professorDTO.getLastName());
        professor.setPassword(professorDTO.getPassword());
        professor.setEmail(professorDTO.getEmail());
        professor.setSalary(professorDTO.getSalary());

        Role role = new Role();
        role.setRoleName("ROLE_" + IRoles.PROFESSOR);
        professor.setRole(role);

        return professor;
    }

}
