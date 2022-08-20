package training.path.academicrecordsystem.controllers.mappers;

import training.path.academicrecordsystem.controllers.dtos.CareerDTO;
import training.path.academicrecordsystem.exceptions.BadResourceDataException;
import training.path.academicrecordsystem.exceptions.NullRequestBodyException;
import training.path.academicrecordsystem.model.Career;

import java.util.Objects;
import java.util.UUID;

public class CareerMapper {

    public static CareerDTO toDTO(Career career) {
        CareerDTO careerDTO = new CareerDTO();
        careerDTO.setId(career.getId());
        careerDTO.setName(career.getName());
        return careerDTO;
    }

    public static Career toEntity(CareerDTO careerDTO) {
        //validateDTO(careerDTO);
        Career careerEntity = new Career();
        careerEntity.setId(careerDTO.getId());
        careerEntity.setName(careerDTO.getName());
        return careerEntity;
    }

    public static Career createEntity(CareerDTO careerDTO) {
        //validateDTO(careerDTO);
        Career careerEntity = new Career();
        careerEntity.setId(UUID.randomUUID().toString());
        careerEntity.setName(careerDTO.getName());
        return careerEntity;
    }

    private static void validateDTO(CareerDTO careerDTO) throws BadResourceDataException, NullRequestBodyException {
        if (Objects.isNull(careerDTO)) throw new NullRequestBodyException("You must provide information to ");
        if (Objects.isNull(careerDTO.getName())) throw new BadResourceDataException("Career name cannot be null");
        if (careerDTO.getName().isEmpty()) throw new BadResourceDataException("You must provide the career name");
        if (careerDTO.getName().isBlank()) throw new BadResourceDataException("Career name cannot be just blank spaces");
    }

}
