package training.path.academicrecordsystem.services;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import training.path.academicrecordsystem.exceptions.ResourceNotFoundException;
import training.path.academicrecordsystem.model.Professor;
import training.path.academicrecordsystem.repositories.implementations.JdbcProfessorRepository;
import training.path.academicrecordsystem.services.implementations.ProfessorService;

import java.util.List;
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
		String id = UUID.randomUUID().toString();
		Professor professor = Professor.builder().id(id).name("Maria").email("maria@email.com").salary(35000).build();

		when(jdbcProfessorRepository.exists(anyString())).thenReturn(true);
		when(jdbcProfessorRepository.update(anyString(), any())).thenReturn(1);

		assertDoesNotThrow(() -> professorService.update(id, professor));
	}

	@Test
	void givenNonExistingProfessor_whenUpdate_thenItThrowsException() {
		String id = UUID.randomUUID().toString();
		Professor professor = Professor.builder().id(id).name("Null").email("null@email.com").salary(0).build();

		when(jdbcProfessorRepository.exists(anyString())).thenReturn(false);
		when(jdbcProfessorRepository.update(anyString(), any())).thenReturn(0);

		assertThrows(ResourceNotFoundException.class, () -> professorService.update(id, professor));
	}

	@Test
	void givenValidId_whenDeleteById_thenItDoesNotThrowException() {
		String id = UUID.randomUUID().toString();

		when(jdbcProfessorRepository.exists(anyString())).thenReturn(true);
		when(jdbcProfessorRepository.deleteById(anyString())).thenReturn(1);

		assertDoesNotThrow(() -> professorService.deleteById(id));
	}

	@Test
	void givenNonExistingId_whenDeleteById_thenThrowException() {
		String id = UUID.randomUUID().toString();

		when(jdbcProfessorRepository.exists(anyString())).thenReturn(false);
		when(jdbcProfessorRepository.deleteById(anyString())).thenReturn(0);

		assertThrows(ResourceNotFoundException.class, () -> professorService.deleteById(id));
	}

	@Test
	void givenValidId_whenFindById_thenReturnProfessor() {
		String id = UUID.randomUUID().toString();
		Professor professor = Professor.builder().id(id).name("Juan").email("juan@email.co").salary(35000).build();

		when(jdbcProfessorRepository.findById(anyString())).thenReturn(Optional.of(professor));

		assertDoesNotThrow(() -> professorService.findById(id));
	}

	@Test
	void givenInvalidId_whenFindById_thenThrowException() {
		String id = "000-123";

		when(jdbcProfessorRepository.findById(anyString())).thenReturn(Optional.empty());

		assertThrows(ResourceNotFoundException.class, () -> professorService.findById(id));
	}

	@Test
	void givenExistingProfessors_whenFindAll_thenItReturnsListOfProfessors() {
		String professorId1 = UUID.randomUUID().toString();
		String professorId2 = UUID.randomUUID().toString();
		String professorId3 = UUID.randomUUID().toString();

		Professor professor1 = Professor.builder().id(professorId1).name("Professor 1").salary(30000).build();
		Professor professor2 = Professor.builder().id(professorId2).name("Professor 2").salary(35000).build();
		Professor professor3 = Professor.builder().id(professorId3).name("Professor 3").salary(40000).build();

		List<Professor> professors = List.of(professor1, professor2, professor3);

		when(jdbcProfessorRepository.findAll()).thenReturn(professors);

		List<Professor> responseList = professorService.findAll();

		assertEquals(professorId1, responseList.get(0).getId());
		assertEquals(professorId2, responseList.get(1).getId());
		assertEquals(professorId3, responseList.get(2).getId());

		assertEquals("Professor 1", responseList.get(0).getName());
		assertEquals("Professor 2", responseList.get(1).getName());
		assertEquals("Professor 3", responseList.get(2).getName());

	}

}