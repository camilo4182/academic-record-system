package training.path.academicrecordsystem.services.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import training.path.academicrecordsystem.dtos.CareerDTO;
import training.path.academicrecordsystem.exceptions.BadArgumentsException;
import training.path.academicrecordsystem.exceptions.CouldNotPerformDBOperationException;
import training.path.academicrecordsystem.exceptions.NotFoundResourceException;
import training.path.academicrecordsystem.mappers.CareerMapper;
import training.path.academicrecordsystem.model.Career;
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
    public CareerDTO save(CareerDTO careerDTO) throws BadArgumentsException {
        if (careerDTO.getName().isBlank()) throw new BadArgumentsException("Name cannot be empty");
        Career careerEntity = CareerMapper.createEntity(careerDTO);
        careerRepository.save(careerEntity);
        Career savedCareerEntity = careerRepository.findByName(careerEntity.getName()).orElseThrow();
        return CareerMapper.createDTO(savedCareerEntity);
    }

    @Override
    public CareerDTO update(long id, CareerDTO careerDTO) throws NotFoundResourceException, BadArgumentsException {
        Optional<Career> foundCareerOptional = careerRepository.findById(id);
        if (foundCareerOptional.isEmpty()) throw new NotFoundResourceException("Career " + careerDTO.getName() + " was not found");
        if (careerDTO.getName().isBlank()) throw new BadArgumentsException("Name cannot be empty");
        Career foundCareer = foundCareerOptional.orElseThrow();
        Career newCareerInfo = CareerMapper.createEntity(careerDTO);
        newCareerInfo.setId(foundCareer.getId());
        careerRepository.update(id, newCareerInfo);
        return CareerMapper.createDTO(foundCareer);
    }

    @Override
    public CareerDTO deleteById(long id) throws NotFoundResourceException, CouldNotPerformDBOperationException {
        Optional<Career> foundCareerOptional = careerRepository.findById(id);
        if (foundCareerOptional.isEmpty()) throw new NotFoundResourceException("Career with id " + id + " was not found to delete");
        int deletedRows = careerRepository.deleteById(id);
        if (deletedRows == 0) throw new CouldNotPerformDBOperationException("Could not perform deletion for career with id " + id);
        return CareerMapper.createDTO(foundCareerOptional.orElseThrow());
    }

    @Override
    public CareerDTO findById(long id) throws NotFoundResourceException {
        Optional<Career> careerOptional = careerRepository.findById(id);
        if (careerOptional.isEmpty()) throw new NotFoundResourceException("Career with id " + id + " does nt exist");
        return CareerMapper.createDTO(careerOptional.orElseThrow());
    }

    @Override
    public CareerDTO findByName(String name) throws NotFoundResourceException {
        Optional<Career> careerOptional = careerRepository.findByName(name);
        if (careerOptional.isEmpty()) throw new NotFoundResourceException("Career " + name + " is not register in the record system");
        return CareerMapper.createDTO(careerOptional.orElseThrow());
    }

    @Override
    public List<CareerDTO> findAll() throws NotFoundResourceException {
        List<Career> careers = careerRepository.findAll();
        if (careers.isEmpty()) throw new NotFoundResourceException("There are not careers register in the system yet");
        return careers.stream().map(CareerMapper::createDTO).toList();
    }
}
