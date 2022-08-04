package training.path.academicrecordsystem.mappers;

import training.path.academicrecordsystem.dtos.CareerDTO;
import training.path.academicrecordsystem.model.Career;

public class CareerMapper {

    public CareerDTO createDTO(Career career) {
        CareerDTO careerDTO = new CareerDTO();
        careerDTO.setId(career.getId());
        careerDTO.setName(career.getName());
        return careerDTO;
    }

    public Career createEntity(CareerDTO careerDTO) {
        Career career = new Career();
        career.setName(careerDTO.getName());
        return career;
    }

}
