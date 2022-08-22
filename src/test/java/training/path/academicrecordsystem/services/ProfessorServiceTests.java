package training.path.academicrecordsystem.services;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import training.path.academicrecordsystem.exceptions.ResourceNotFoundException;
import training.path.academicrecordsystem.exceptions.UniqueColumnViolationException;
import training.path.academicrecordsystem.model.Professor;
import training.path.academicrecordsystem.repositories.implementations.ProfessorRepository;
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
    ProfessorRepository professorRepository;

	@InjectMocks
	ProfessorService professorService;

	@Test
	void givenValidProfessorData_whenSave_thenItDoesNotThrowException() {
		Professor professor = Professor.builder().id(UUID.randomUUID().toString()).name("Andres").email("andres@email.com").salary(35000).build();

		when(professorRepository.save(any())).thenReturn(1);

		assertDoesNotThrow(() -> professorService.save(professor));
	}

	@Test
	void givenTwoProfessorsWithSameData_whenSave_thenItThrowsException() {
		Professor professor1 = Professor.builder().id(UUID.randomUUID().toString()).name("Andres").email("andres@email.com").salary(35000).build();
		Professor professor2 = Professor.builder().id(UUID.randomUUID().toString()).name("Andres").email("andres@email.com").salary(35000).build();

		when(professorRepository.save(any())).thenReturn(0);
		when(professorRepository.findByName(anyString())).thenReturn(Optional.of(professor2));
		when(professorRepository.findByEmail(anyString())).thenReturn(Optional.of(professor2));

		assertThrows(UniqueColumnViolationException.class, () -> professorService.save(professor1));
	}

	@Test
	void givenTwoProfessorsWithSameName_whenSave_thenItThrowsException() {
		Professor professor1 = Professor.builder().id(UUID.randomUUID().toString()).name("Andres").email("other@email.com").salary(35000).build();
		Professor professor2 = Professor.builder().id(UUID.randomUUID().toString()).name("Andres").email("another@email.com").salary(35000).build();

		when(professorRepository.save(any())).thenReturn(0);
		when(professorRepository.findByName(anyString())).thenReturn(Optional.of(professor2));

		assertThrows(UniqueColumnViolationException.class, () -> professorService.save(professor1));
	}

	@Test
	void givenTwoProfessorsWithSameEmail_whenSave_thenItThrowsException() {
		Professor professor1 = Professor.builder().id(UUID.randomUUID().toString()).name("Juan").email("same@email.com").salary(35000).build();
		Professor professor2 = Professor.builder().id(UUID.randomUUID().toString()).name("Andres").email("same@email.com").salary(35000).build();

		when(professorRepository.save(any())).thenReturn(0);
		when(professorRepository.findByEmail(anyString())).thenReturn(Optional.of(professor2));

		assertThrows(UniqueColumnViolationException.class, () -> professorService.save(professor1));
	}

	@Test
	void givenExistingProfessor_whenUpdate_thenItDoesNotThrowException() {
		String id = UUID.randomUUID().toString();
		Professor professor = Professor.builder().id(id).name("Maria").email("maria@email.com").salary(35000).build();

		when(professorRepository.exists(anyString())).thenReturn(true);
		when(professorRepository.update(anyString(), any())).thenReturn(1);

		assertDoesNotThrow(() -> professorService.update(id, professor));
	}

	@Test
	void givenNonExistingProfessor_whenUpdate_thenItThrowsException() {
		String id = UUID.randomUUID().toString();
		Professor professor = Professor.builder().id(id).name("Null").email("null@email.com").salary(0).build();

		when(professorRepository.exists(anyString())).thenReturn(false);
		when(professorRepository.update(anyString(), any())).thenReturn(0);

		assertThrows(ResourceNotFoundException.class, () -> professorService.update(id, professor));
	}

	@Test
	void givenValidId_whenDeleteById_thenItDoesNotThrowException() {
		String id = UUID.randomUUID().toString();

		when(professorRepository.exists(anyString())).thenReturn(true);
		when(professorRepository.deleteById(anyString())).thenReturn(1);

		assertDoesNotThrow(() -> professorService.deleteById(id));
	}

	@Test
	void givenNonExistingId_whenDeleteById_thenThrowException() {
		String id = UUID.randomUUID().toString();

		when(professorRepository.exists(anyString())).thenReturn(false);
		when(professorRepository.deleteById(anyString())).thenReturn(0);

		assertThrows(ResourceNotFoundException.class, () -> professorService.deleteById(id));
	}

	@Test
	void givenValidId_whenFindById_thenReturnProfessor() {
		String id = UUID.randomUUID().toString();
		Professor professor = Professor.builder().id(id).name("Juan").email("juan@email.co").salary(35000).build();

		when(professorRepository.findById(anyString())).thenReturn(Optional.of(professor));

		assertDoesNotThrow(() -> professorService.findById(id));
	}

	@Test
	void givenInvalidId_whenFindById_thenThrowException() {
		String id = "000-123";

		when(professorRepository.findById(anyString())).thenReturn(Optional.empty());

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

		when(professorRepository.findAll()).thenReturn(professors);

		List<Professor> responseList = professorService.findAll();

		assertEquals(professorId1, responseList.get(0).getId());
		assertEquals(professorId2, responseList.get(1).getId());
		assertEquals(professorId3, responseList.get(2).getId());

		assertEquals("Professor 1", responseList.get(0).getName());
		assertEquals("Professor 2", responseList.get(1).getName());
		assertEquals("Professor 3", responseList.get(2).getName());

	}

}