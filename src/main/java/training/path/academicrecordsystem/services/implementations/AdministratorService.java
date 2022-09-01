package training.path.academicrecordsystem.services.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import training.path.academicrecordsystem.exceptions.ResourceNotFoundException;
import training.path.academicrecordsystem.model.Administrator;
import training.path.academicrecordsystem.model.Role;
import training.path.academicrecordsystem.repositories.interfaces.IAdminRepository;
import training.path.academicrecordsystem.repositories.interfaces.IUserRepository;
import training.path.academicrecordsystem.security.interfaces.IRoles;
import training.path.academicrecordsystem.services.interfaces.IAdminService;

import java.util.Optional;

@Service
public class AdministratorService implements IAdminService {

    private IAdminRepository adminRepository;
    private IUserRepository userRepository;

    @Autowired
    public AdministratorService(IAdminRepository adminRepository, IUserRepository userRepository) {
        this.adminRepository = adminRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void save(Administrator administrator) throws ResourceNotFoundException {
        Role role = userRepository.findRoleByName(administrator.getRole().getRoleName()).orElseThrow(
                () -> new ResourceNotFoundException("The role " + administrator.getRole().getRoleName() + " is not available"));
        administrator.setRole(role);
        adminRepository.save(administrator);
    }

}
