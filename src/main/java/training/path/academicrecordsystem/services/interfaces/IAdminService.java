package training.path.academicrecordsystem.services.interfaces;

import training.path.academicrecordsystem.exceptions.ResourceNotFoundException;
import training.path.academicrecordsystem.model.Administrator;

public interface IAdminService {

    void save(Administrator administrator) throws ResourceNotFoundException;

}
