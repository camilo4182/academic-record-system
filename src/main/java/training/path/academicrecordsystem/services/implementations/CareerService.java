package training.path.academicrecordsystem.services.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import training.path.academicrecordsystem.dtos.CareerDTO;
import training.path.academicrecordsystem.repositories.interfaces.CareerRepository;
import training.path.academicrecordsystem.services.interfaces.ICareerService;

import java.util.List;
import java.util.Optional;

public class CareerService implements ICareerService {

    private final CareerRepository careerRepository;

    @Autowired
    public CareerService(CareerRepository careerRepository) {
        this.careerRepository = careerRepository;
    }

    @Override
    public CareerDTO save(CareerDTO careerDTO) {
        return null;
    }

    @Override
    public CareerDTO update(long id, CareerDTO careerDTO) {
        return null;
    }

    @Override
    public CareerDTO deleteById(long id) {
        return null;
    }

    @Override
    public Optional<CareerDTO> findById(long id) {
        return Optional.empty();
    }

    @Override
    public Optional<CareerDTO> findByName(String name) {
        return Optional.empty();
    }

    @Override
    public List<CareerDTO> findAll() {
        return null;
    }
}
