package training.path.academicrecordsystem.services.interfaces;

import training.path.academicrecordsystem.dtos.CareerDTO;

import java.util.List;
import java.util.Optional;

public interface ICareerService {

    CareerDTO save(CareerDTO careerDTO);
    CareerDTO update(long id, CareerDTO careerDTO);
    CareerDTO deleteById(long id);
    Optional<CareerDTO> findById(long id);
    Optional<CareerDTO> findByName(String name);
    List<CareerDTO> findAll();

}
