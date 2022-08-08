package training.path.academicrecordsystem.services;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import training.path.academicrecordsystem.exceptions.BadArgumentsException;
import training.path.academicrecordsystem.exceptions.NotFoundResourceException;
import training.path.academicrecordsystem.model.Professor;
import training.path.academicrecordsystem.repositories.implementations.JdbcProfessorRepository;
import training.path.academicrecordsystem.services.implementations.ProfessorService;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest
class ProfessorServiceTests {

	@Mock
	JdbcProfessorRepository jdbcProfessorRepository;

	@InjectMocks
	ProfessorService professorService;

	@Test
	void givenValidProfessorData_whenSave_thenItDoesNotThrowException() {
		Professor professor = Professor.builder().id(UUID.randomUUID().toString()).name("Andres").email("andres@email.com").salary(35000).build();

		when(jdbcProfessorRepository.save(any())).thenReturn(1);

		assertDoesNotThrow(() -> professorService.save(professor));
	}

	@Test
	void givenExistingProfessor_whenUpdate_thenItDoesNotThrowException() {
		Professor professor = Professor.builder().id("1").name("Maria").email("maria@email.com").salary(35000).build();

		when(jdbcProfessorRepository.exists(anyString())).thenReturn(true);
		when(jdbcProfessorRepository.update(anyString(), any())).thenReturn(1);

		assertDoesNotThrow(() -> professorService.update("1", professor));
	}

	@Test
	void givenNonExistingProfessor_whenUpdate_thenItThrowsException() {
		Professor professor = Professor.builder().id("000").name("Null").email("null@email.com").salary(0).build();

		when(jdbcProfessorRepository.exists(anyString())).thenReturn(false);
		when(jdbcProfessorRepository.update(anyString(), any())).thenReturn(0);

		assertThrows(NotFoundResourceException.class, () -> professorService.update("000", professor));
	}


	void givenEmptyName_whenUpdate_thenThrowException() {

	}

	@Test
	void givenValidId_whenDeleteById_thenItDoesNotThrowException() {
		String id = "1";

		when(jdbcProfessorRepository.exists(anyString())).thenReturn(true);
		when(jdbcProfessorRepository.deleteById(anyString())).thenReturn(1);

		assertDoesNotThrow(() -> professorService.deleteById(id));
	}

	@Test
	void givenNonExistingId_whenDeleteById_thenThrowException() {
		String id = "000";

		when(jdbcProfessorRepository.exists(anyString())).thenReturn(false);
		when(jdbcProfessorRepository.deleteById(anyString())).thenReturn(0);

		assertThrows(NotFoundResourceException.class, () -> professorService.deleteById(id));
	}

	@Test
	void givenValidId_whenFindById_thenReturnProfessor() {
		Professor professor = Professor.builder().id("1").name("Juan").email("juan@email.co").salary(35000).build();

		when(jdbcProfessorRepository.findById(anyString())).thenReturn(Optional.of(professor));

		assertDoesNotThrow(() -> professorService.findById("1"));
	}

	@Test
	void givenInvalidId_whenFindById_thenThrowException() {
		Professor professor = Professor.builder().id("000").name("Juan").email("juan@email.co").salary(35000).build();

		when(jdbcProfessorRepository.findById(anyString())).thenReturn(Optional.empty());

		assertThrows(NotFoundResourceException.class, () -> professorService.findById("000"));
	}

}