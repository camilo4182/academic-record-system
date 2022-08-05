package training.path.academicrecordsystem.services;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import training.path.academicrecordsystem.dtos.CareerDTO;
import training.path.academicrecordsystem.exceptions.BadArgumentsException;
import training.path.academicrecordsystem.exceptions.CouldNotPerformDBOperationException;
import training.path.academicrecordsystem.exceptions.NotFoundResourceException;
import training.path.academicrecordsystem.mappers.CareerMapper;
import training.path.academicrecordsystem.model.Career;
import training.path.academicrecordsystem.repositories.interfaces.CareerRepository;
import training.path.academicrecordsystem.services.implementations.CareerService;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@SpringBootTest
public class CareerServiceTests {

    @Mock
    CareerRepository careerRepository;

    @InjectMocks
    CareerService careerService;

    CareerDTO careerDTO;
    Career careerEntity;

    void setupPreConditions(String careerName) {
        careerDTO = new CareerDTO();
        careerDTO.setName(careerName);
        careerEntity = CareerMapper.createEntity(careerDTO);
    }

    @Test
    void givenValidCareerDTO_whenSave_thenItReturnsTheCareer() throws BadArgumentsException {
        setupPreConditions("System Engineering");

        when(careerRepository.save(any())).thenReturn(1);
        when(careerRepository.findByName(anyString())).thenReturn(Optional.of(careerEntity));

        assertDoesNotThrow(() -> careerService.save(careerDTO));
        CareerDTO savedCareerDTO = careerService.save(careerDTO);
        assertEquals("System Engineering", savedCareerDTO.getName());
    }

    @Test
    void givenEmptyName_whenSave_thenItThrowsException() {
        setupPreConditions("");

        when(careerRepository.save(any())).thenReturn(0);
        when(careerRepository.findByName(anyString())).thenReturn(Optional.empty());

        assertThrows(BadArgumentsException.class, () -> careerService.save(careerDTO));
    }

    @Test
    void givenWhiteSpaceName_whenSave_thenItThrowsException() {
        setupPreConditions(" ");

        when(careerRepository.save(any())).thenReturn(0);
        when(careerRepository.findByName(anyString())).thenReturn(Optional.empty());

        assertThrows(BadArgumentsException.class, () -> careerService.save(careerDTO));
    }

    @Test
    void givenValidNewCareerInfoAndExistingCareer_whenUpdate_thenItReturnsTheUpdatedCareer() throws NotFoundResourceException, BadArgumentsException {
        setupPreConditions("Medicine");

        when(careerRepository.findById(anyLong())).thenReturn(Optional.of(careerEntity));
        when(careerRepository.update(anyLong(), any())).thenReturn(1);

        assertDoesNotThrow(() -> careerService.update(1L, careerDTO));
        CareerDTO updatedCareerDTO = careerService.update(1L, careerDTO);
        assertEquals("Medicine", updatedCareerDTO.getName());
    }

    @Test
    void givenInvalidNewCareerInfoAndExistingCareer_whenUpdate_thenItThrowsException() {
        setupPreConditions("");

        when(careerRepository.findById(anyLong())).thenReturn(Optional.of(careerEntity));
        when(careerRepository.update(anyLong(), any())).thenReturn(0);

        assertThrows(BadArgumentsException.class, () -> careerService.update(1L, careerDTO));
    }

    @Test
    void givenValidNewCareerInfoAndNonExistingCareer_whenUpdate_thenItReturnsTheUpdatedCareer() {
        setupPreConditions("Music");

        when(careerRepository.findById(anyLong())).thenReturn(Optional.empty());
        when(careerRepository.update(anyLong(), any())).thenReturn(0);

        assertThrows(NotFoundResourceException.class, () -> careerService.update(1L, careerDTO));
    }

    @Test
    void givenExistingCareer_whenDeleteById_thenItReturnsTheDeletedCareer() throws NotFoundResourceException, CouldNotPerformDBOperationException {
        setupPreConditions("Chemistry");

        when(careerRepository.findById(anyLong())).thenReturn(Optional.of(careerEntity));
        when(careerRepository.deleteById(anyLong())).thenReturn(1);

        assertDoesNotThrow(() -> careerService.deleteById(1L));
        CareerDTO deletedCareerDTO = careerService.deleteById(1L);
        assertEquals("Chemistry", deletedCareerDTO.getName());
    }

    @Test
    void givenNonExistingCareer_whenDeleteById_thenItThrowsException() {
        setupPreConditions("Art");

        when(careerRepository.findById(anyLong())).thenReturn(Optional.empty());
        when(careerRepository.deleteById(anyLong())).thenReturn(0);

        assertThrows(NotFoundResourceException.class, () -> careerService.deleteById(1L));
    }

    @Test
    void givenValidId_whenFindById_thenItReturnsTheCareerDTO() throws NotFoundResourceException {
        setupPreConditions("Economics");

        when(careerRepository.findById(anyLong())).thenReturn(Optional.of(careerEntity));

        assertDoesNotThrow(() -> careerService.findById(1L));
        CareerDTO careerDTO = careerService.findById(1L);
        assertEquals("Economics", careerDTO.getName());
    }

    @Test
    void givenInvalidId_whenFindById_thenItThrowsException() {
        setupPreConditions("Economics");

        when(careerRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(NotFoundResourceException.class, () -> careerService.findById(100000L));
    }

    @Test
    void givenValidName_whenFindByName_thenItReturnsCareerDTO() throws NotFoundResourceException {
        setupPreConditions("Literature");

        when(careerRepository.findByName(anyString())).thenReturn(Optional.of(careerEntity));

        assertDoesNotThrow(() -> careerService.findByName("Literature"));
        CareerDTO foundCareerDTO = careerService.findByName("Literature");
        assertEquals("Literature", foundCareerDTO.getName());
    }

    @Test
    void givenInvalidName_whenFindByName_thenItThrowsException() {
        setupPreConditions("Random text");

        when(careerRepository.findByName(anyString())).thenReturn(Optional.empty());

        assertThrows(NotFoundResourceException.class, () -> careerService.findByName("Random text"));
    }

    @Test
    void givenRegisteredCareers_whenFindAll_thenItReturnsListOfCareerDTO() throws NotFoundResourceException {
        List<Career> careerList = List.of(
                new Career("Medicine"),
                new Career("System Engineering"),
                new Career("Economics")
        );

        when(careerRepository.findAll()).thenReturn(careerList);

        assertDoesNotThrow(() -> careerService.findAll());
        List<CareerDTO> careerDTOList = careerService.findAll();
        assertEquals(3, careerDTOList.size());
        assertEquals("Medicine", careerDTOList.get(0).getName());
        assertEquals("System Engineering", careerDTOList.get(1).getName());
        assertEquals("Economics", careerDTOList.get(2).getName());
    }

    @Test
    void givenThereAreNotCareers_whenFindAll_thenItThrowsException() {
        List<Career> emptyCareerList = List.of();

        when(careerRepository.findAll()).thenReturn(emptyCareerList);

        assertThrows(NotFoundResourceException.class, () -> careerService.findAll());
    }

}
