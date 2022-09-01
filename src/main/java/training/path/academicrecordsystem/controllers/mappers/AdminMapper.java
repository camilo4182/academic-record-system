package training.path.academicrecordsystem.controllers.mappers;

import training.path.academicrecordsystem.controllers.dtos.AdminDTO;
import training.path.academicrecordsystem.model.Administrator;
import training.path.academicrecordsystem.model.Role;
import training.path.academicrecordsystem.security.interfaces.IRoles;

import java.util.UUID;

public class AdminMapper {

    public static Administrator createEntity(AdminDTO adminDTO) {
        Administrator administrator = new Administrator();
        administrator.setId(UUID.randomUUID().toString());
        administrator.setFirstName(adminDTO.getFirstName());
        administrator.setLastName(adminDTO.getLastName());
        administrator.setUserName(adminDTO.getFirstName().toLowerCase() + "." + adminDTO.getLastName().toLowerCase());
        administrator.setPassword(adminDTO.getPassword());
        administrator.setEmail(adminDTO.getEmail());

        Role role = new Role();
        role.setRoleName("ROLE_" + IRoles.ADMIN);
        administrator.setRole(role);

        return administrator;
    }

}
