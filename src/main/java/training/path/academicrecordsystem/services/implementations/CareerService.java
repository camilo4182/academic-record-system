package training.path.academicrecordsystem.services.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import training.path.academicrecordsystem.controllers.dtos.CareerDTO;
import training.path.academicrecordsystem.exceptions.BadArgumentsException;
import training.path.academicrecordsystem.exceptions.CouldNotPerformDBOperationException;
import training.path.academicrecordsystem.exceptions.NotFoundResourceException;
import training.path.academicrecordsystem.controllers.mappers.CareerMapper;
import training.path.academicrecordsystem.model.Career;
import training.path.academicrecordsystem.repositories.interfaces.CareerRepository;
import training.path.academicrecordsystem.services.interfaces.ICareerService;

import java.util.List;
import java.util.Optional;

@Service
public class CareerService implements ICareerService {

    private final CareerRepository careerRepository;

    @Autowired
    public CareerService(CareerRepository careerRepository) {
        this.careerRepository = careerRepository;
    }

    @Override
    public void save(Career career) {
        careerRepository.save(career);
    }

    @Override
    public void update(Career career) throws NotFoundResourceException {
        if (!careerRepository.exists(career.getId())) throw new NotFoundResourceException("Career " + career.getName() + " was not found");
        careerRepository.update(career.getId(), career);
    }

    @Override
    public void deleteById(String id) throws NotFoundResourceException {
        if (!careerRepository.exists(id)) throw new NotFoundResourceException("Career " + id + " was not found");
        careerRepository.deleteById(id);
    }

    @Override
    public Career findById(String id) throws NotFoundResourceException {
        return careerRepository.findById(id).orElseThrow(() -> new NotFoundResourceException("Career with id " + id + " does not exist"));
    }

    @Override
    public Career findByName(String name) throws NotFoundResourceException {
        return careerRepository.findByName(name).orElseThrow(() -> new NotFoundResourceException("Career with  " + name + " does not exist"));
    }

    @Override
    public List<Career> findAll() {
        return careerRepository.findAll();
    }
}
