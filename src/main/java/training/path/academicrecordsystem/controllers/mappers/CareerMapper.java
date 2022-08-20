package training.path.academicrecordsystem.controllers.mappers;

import training.path.academicrecordsystem.controllers.dtos.CareerDTO;
import training.path.academicrecordsystem.model.Career;

import java.util.UUID;

public class CareerMapper {

    public static CareerDTO toDTO(Career career) {
        CareerDTO careerDTO = new CareerDTO();
        careerDTO.setId(career.getId());
        careerDTO.setName(career.getName());
        return careerDTO;
    }

    public static Career toEntity(CareerDTO careerDTO) {
        Career careerEntity = new Career();
        careerEntity.setId(careerDTO.getId());
        careerEntity.setName(careerDTO.getName());
        return careerEntity;
    }

    public static Career createEntity(CareerDTO careerDTO) {
        Career careerEntity = new Career();
        careerEntity.setId(UUID.randomUUID().toString());
        careerEntity.setName(careerDTO.getName());
        return careerEntity;
    }

}
