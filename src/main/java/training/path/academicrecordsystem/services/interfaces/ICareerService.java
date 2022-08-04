package training.path.academicrecordsystem.services.interfaces;

import training.path.academicrecordsystem.dtos.CareerDTO;
import training.path.academicrecordsystem.exceptions.BadArgumentsException;
import training.path.academicrecordsystem.exceptions.CouldNotPerformDBOperationException;
import training.path.academicrecordsystem.exceptions.NotFoundResourceException;

import java.util.List;
import java.util.Optional;

public interface ICareerService {

    CareerDTO save(CareerDTO careerDTO) throws BadArgumentsException;
    CareerDTO update(long id, CareerDTO careerDTO) throws NotFoundResourceException, BadArgumentsException;
    CareerDTO deleteById(long id) throws NotFoundResourceException, CouldNotPerformDBOperationException;
    Optional<CareerDTO> findById(long id);
    Optional<CareerDTO> findByName(String name);
    List<CareerDTO> findAll();

}
