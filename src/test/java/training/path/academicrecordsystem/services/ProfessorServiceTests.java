package training.path.academicrecordsystem.services;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import training.path.academicrecordsystem.exceptions.ResourceNotFoundException;
import training.path.academicrecordsystem.exceptions.UniqueColumnViolationException;
import training.path.academicrecordsystem.model.CourseClass;
import training.path.academicrecordsystem.model.Professor;
import training.path.academicrecordsystem.model.Role;
import training.path.academicrecordsystem.repositories.implementations.ProfessorRepository;
import training.path.academicrecordsystem.repositories.implementations.UserRepository;
import training.path.academicrecordsystem.security.interfaces.IRoles;
import training.path.academicrecordsystem.services.implementations.ProfessorService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class ProfessorServiceTests {

	@Mock
	UserRepository userRepository;

	@Mock
    ProfessorRepository professorRepository;

	@InjectMocks
	ProfessorService professorService;

	@Test
	void givenValidProfessorData_whenSave_thenItDoesNotThrowException() {
		String professorID = UUID.randomUUID().toString();
		Role role = Role.builder().id(UUID.randomUUID().toString()).roleName(IRoles.PROFESSOR).build();
		Professor professor = Professor.builder().id(professorID)
				.firstName("Juan")
				.lastName("Rodriguez")
				.userName("juan.rodriguez")
				.email("juan.rodriguez@email.com")
				.salary(35000)
				.role(role)
				.build();

		when(userRepository.findRoleByName(IRoles.PROFESSOR)).thenReturn(Optional.of(role));
		when(professorRepository.findByUserName(professorID)).thenReturn(Optional.of(professor));
		when(professorRepository.findByEmail(professor.getEmail())).thenReturn(Optional.of(professor));
		when(professorRepository.save(any())).thenReturn(1);

		assertDoesNotThrow(() -> professorService.save(professor));
	}

	@Test
	void givenUnregisteredRole_whenSave_thenItThrowsException() {
		String professorID = UUID.randomUUID().toString();
		Role role = Role.builder().id(UUID.randomUUID().toString()).roleName(IRoles.PROFESSOR).build();
		Professor professor = Professor.builder().id(professorID)
				.firstName("Juan")
				.lastName("Rodriguez")
				.userName("juan.rodriguez")
				.email("juan.rodriguez@email.com")
				.salary(35000)
				.role(role)
				.build();

		when(userRepository.findRoleByName(IRoles.PROFESSOR)).thenReturn(Optional.empty());
		when(professorRepository.findByUserName(professorID)).thenReturn(Optional.of(professor));
		when(professorRepository.findByEmail(professor.getEmail())).thenReturn(Optional.of(professor));
		when(professorRepository.save(any())).thenReturn(1);

		assertThrows(ResourceNotFoundException.class, () -> professorService.save(professor));
	}

	@Test
	void givenTwoProfessorsWithSameData_whenSave_thenItThrowsException() {
		String newProfessorID = UUID.randomUUID().toString();
		String registeredProfessorID = UUID.randomUUID().toString();
		Role role = Role.builder().id(UUID.randomUUID().toString()).roleName(IRoles.PROFESSOR).build();
		Professor newProfessor = Professor.builder().id(newProfessorID)
				.firstName("Juan")
				.lastName("Rodriguez")
				.userName("juan.rodriguez")
				.email("juan.rodriguez@email.com")
				.salary(35000)
				.role(role)
				.build();

		Professor registeredProfessor = Professor.builder().id(registeredProfessorID)
				.firstName("Juan")
				.lastName("Rodriguez")
				.userName("juan.rodriguez")
				.email("juan.rodriguez@email.com")
				.salary(35000)
				.role(role)
				.build();

		when(userRepository.findRoleByName(IRoles.PROFESSOR)).thenReturn(Optional.of(role));
		when(professorRepository.findByUserName(newProfessor.getUserName())).thenReturn(Optional.of(registeredProfessor));
		when(professorRepository.findByEmail(newProfessor.getEmail())).thenReturn(Optional.of(registeredProfessor));
		when(professorRepository.save(any())).thenReturn(0);

		assertThrows(UniqueColumnViolationException.class, () -> professorService.save(newProfessor));
	}

	@Test
	void givenTwoProfessorsWithSameUserName_whenSave_thenItThrowsException() {
		String newProfessorID = UUID.randomUUID().toString();
		String registeredProfessorID = UUID.randomUUID().toString();
		Role role = Role.builder().id(UUID.randomUUID().toString()).roleName(IRoles.PROFESSOR).build();
		Professor newProfessor = Professor.builder().id(newProfessorID)
				.firstName("Juan")
				.lastName("Rodriguez")
				.userName("juan.rodriguez")
				.email("juan1.rodriguez1@email.com")
				.salary(35000)
				.role(role)
				.build();

		Professor registeredProfessor = Professor.builder().id(registeredProfessorID)
				.firstName("Juan")
				.lastName("Rodriguez")
				.userName("juan.rodriguez")
				.email("juan2.rodriguez2@email.com")
				.salary(35000)
				.role(role)
				.build();

		when(userRepository.findRoleByName(IRoles.PROFESSOR)).thenReturn(Optional.of(role));
		when(professorRepository.findByUserName(newProfessor.getUserName())).thenReturn(Optional.of(registeredProfessor));
		when(professorRepository.findByEmail(newProfessor.getEmail())).thenReturn(Optional.empty());
		when(professorRepository.save(any())).thenReturn(0);

		assertThrows(UniqueColumnViolationException.class, () -> professorService.save(newProfessor));
	}

	@Test
	void givenTwoProfessorsWithSameEmail_whenSave_thenItThrowsException() {
		String newProfessorID = UUID.randomUUID().toString();
		String registeredProfessorID = UUID.randomUUID().toString();
		Role role = Role.builder().id(UUID.randomUUID().toString()).roleName(IRoles.PROFESSOR).build();
		Professor newProfessor = Professor.builder().id(newProfessorID)
				.firstName("Juan")
				.lastName("Rodriguez")
				.userName("juan.rodriguez")
				.email("same@email.com")
				.salary(35000)
				.role(role)
				.build();

		Professor registeredProfessor = Professor.builder().id(registeredProfessorID)
				.firstName("Alejandro")
				.lastName("Magno")
				.userName("alejandro.magno")
				.email("same@email.com")
				.salary(35000)
				.role(role)
				.build();

		when(userRepository.findRoleByName(IRoles.PROFESSOR)).thenReturn(Optional.of(role));
		when(professorRepository.findByUserName(newProfessor.getUserName())).thenReturn(Optional.empty());
		when(professorRepository.findByEmail(newProfessor.getEmail())).thenReturn(Optional.of(registeredProfessor));
		when(professorRepository.save(any())).thenReturn(0);

		assertThrows(UniqueColumnViolationException.class, () -> professorService.save(newProfessor));
	}

	@Test
	void givenExistingProfessor_whenUpdate_thenItDoesNotThrowException() {
		String id = UUID.randomUUID().toString();
		Professor professor = Professor.builder().id(id)
				.firstName("Maria")
				.lastName("Rodriguez")
				.userName("maria.rodriguez")
				.email("maria.rodriguez@email.com")
				.salary(15000)
				.build();

		when(professorRepository.exists(anyString())).thenReturn(true);
		when(professorRepository.update(anyString(), any())).thenReturn(1);

		assertDoesNotThrow(() -> professorService.update(id, professor));
	}

	@Test
	void givenNonExistingProfessor_whenUpdate_thenItThrowsException() {
		String id = UUID.randomUUID().toString();
		Professor professor = Professor.builder().id(id)
				.firstName("Null")
				.lastName("Null")
				.userName("null.null")
				.email("null@email.com")
				.salary(0)
				.build();

		when(professorRepository.exists(anyString())).thenReturn(false);
		when(professorRepository.update(anyString(), any())).thenReturn(0);

		assertThrows(ResourceNotFoundException.class, () -> professorService.update(id, professor));
	}

	@Test
	void givenExistingProfessorWithUpdatedUniqueUserNameAndEmail_whenUpdateBasicInfo_thenItDoesNotThrowException() {
		String professorID = UUID.randomUUID().toString();
		Professor professor = Professor.builder().id(professorID)
				.firstName("Maria")
				.lastName("Rodriguez")
				.userName("maria.rodriguez")
				.email("maria.rodriguez@email.com")
				.salary(35000)
				.build();

		when(professorRepository.exists(professorID)).thenReturn(true);
		when(professorRepository.findByUserName(professor.getUserName())).thenReturn(Optional.empty());
		when(professorRepository.findByEmail(professor.getEmail())).thenReturn(Optional.empty());
		when(professorRepository.update(anyString(), any())).thenReturn(1);

		assertDoesNotThrow(() -> professorService.updateBasicInfo(professorID, professor));
	}

	@Test
	void givenNonExistingProfessor_whenUpdateBasicInfo_thenItThrowsException() {
		String unregisteredProfessorID = UUID.randomUUID().toString();
		Professor unregisteredProfessor = Professor.builder().id(unregisteredProfessorID)
				.firstName("Null")
				.lastName("Null")
				.userName("null.null")
				.email("null@email.com")
				.salary(0)
				.build();

		when(professorRepository.exists(unregisteredProfessorID)).thenReturn(false);
		when(professorRepository.update(anyString(), any())).thenReturn(0);

		assertThrows(ResourceNotFoundException.class, () -> professorService.updateBasicInfo(unregisteredProfessorID, unregisteredProfessor));
	}

	@Test
	void givenNonTwoProfessorsWithSameNameButDifferentLastNames_whenUpdateBasicInfo_thenItDoesNotThrowException() {
		String id = UUID.randomUUID().toString();
		Professor professorToUpdate = Professor.builder().id(id)
				.firstName("Maria")
				.lastName("Rodriguez")
				.userName("maria.rodriguez")
				.email("maria.rodriguez@email.com")
				.salary(40000)
				.build();

		Professor professor2 = Professor.builder().id(id)
				.firstName("Maria")
				.lastName("Giraldo")
				.userName("maria.giraldo")
				.email("maria.giraldo@email.com")
				.salary(30000)
				.build();

		when(professorRepository.exists(anyString())).thenReturn(true);
		when(professorRepository.findByUserName(professorToUpdate.getUserName())).thenReturn(Optional.of(professor2));
		when(professorRepository.update(anyString(), any())).thenReturn(0);

		assertDoesNotThrow(() -> professorService.updateBasicInfo(id, professorToUpdate));
	}

	@Test
	void givenNonTwoProfessorsWithSameUserName_whenUpdateBasicInfo_thenItThrowsException() {
		String id = UUID.randomUUID().toString();
		Professor professorToUpdate = Professor.builder().id(id)
				.firstName("Maria")
				.lastName("Rodriguez")
				.userName("maria.rodriguez")
				.email("one@email.com")
				.salary(40000)
				.build();

		Professor professor2 = Professor.builder().id(UUID.randomUUID().toString())
				.firstName("Maria")
				.lastName("Rodriguez")
				.userName("maria.rodriguez")
				.email("another@email.com")
				.salary(30000)
				.build();

		when(professorRepository.exists(anyString())).thenReturn(true);
		when(professorRepository.findByUserName(professorToUpdate.getUserName())).thenReturn(Optional.of(professor2));
		when(professorRepository.update(anyString(), any())).thenReturn(0);

		assertThrows(UniqueColumnViolationException.class, () -> professorService.updateBasicInfo(id, professorToUpdate));
	}

	@Test
	void givenNonTwoProfessorsWithSameEmail_whenUpdateBasicInfo_thenItThrowsException() {
		String id = UUID.randomUUID().toString();
		Professor professorToUpdate = Professor.builder().id(id)
				.firstName("Juan")
				.lastName("Velasquez")
				.userName("juan.velasquez")
				.email("juan@email.com")
				.salary(40000)
				.build();

		Professor professor2 = Professor.builder().id(UUID.randomUUID().toString())
				.firstName("Juan")
				.lastName("Rodriguez")
				.userName("juan.rodriguez")
				.email("juan@email.com")
				.salary(30000)
				.build();

		when(professorRepository.exists(anyString())).thenReturn(true);
		when(professorRepository.findByUserName(professorToUpdate.getUserName())).thenReturn(Optional.empty());
		when(professorRepository.findByEmail(professorToUpdate.getEmail())).thenReturn(Optional.of(professor2));
		when(professorRepository.update(anyString(), any())).thenReturn(0);

		assertThrows(UniqueColumnViolationException.class, () -> professorService.updateBasicInfo(id, professorToUpdate));
	}

	@Test
	void givenValidId_whenDeleteById_thenItDoesNotThrowException() {
		String id = UUID.randomUUID().toString();

		when(professorRepository.exists(id)).thenReturn(true);
		when(professorRepository.deleteById(id)).thenReturn(1);

		assertDoesNotThrow(() -> professorService.deleteById(id));
	}

	@Test
	void givenNonExistingId_whenDeleteById_thenThrowException() {
		String id = UUID.randomUUID().toString();

		when(professorRepository.exists(id)).thenReturn(false);
		when(professorRepository.deleteById(anyString())).thenReturn(0);

		assertThrows(ResourceNotFoundException.class, () -> professorService.deleteById(id));
	}

	@Test
	void givenValidId_whenFindById_thenReturnProfessor() {
		String id = UUID.randomUUID().toString();
		Professor professor = Professor.builder().id(id)
				.firstName("Juan")
				.lastName("Rodriguez")
				.userName("juan.rodriguez")
				.email("juan@email.co")
				.salary(35000)
				.build();

		when(professorRepository.findById(anyString())).thenReturn(Optional.of(professor));

		assertDoesNotThrow(() -> professorService.findById(id));
	}

	@Test
	void givenNonExistingProfessor_whenFindById_thenThrowException() {
		String nonExistingProfessorID = UUID.randomUUID().toString();

		when(professorRepository.findById(nonExistingProfessorID)).thenReturn(Optional.empty());

		assertThrows(ResourceNotFoundException.class, () -> professorService.findById(nonExistingProfessorID));
	}

	@Test
	void givenExistingProfessors_whenFindAll_thenItReturnsListOfProfessors() {
		String professorId1 = UUID.randomUUID().toString();
		String professorId2 = UUID.randomUUID().toString();
		String professorId3 = UUID.randomUUID().toString();

		Professor professor1 = Professor.builder().id(professorId1)
				.firstName("Professor1")
				.lastName("Test1")
				.userName("professor1.test1")
				.salary(30000)
				.build();

		Professor professor2 = Professor.builder().id(professorId2)
				.firstName("Professor2")
				.lastName("Test2")
				.userName("professor2.test2")
				.salary(35000)
				.build();

		Professor professor3 = Professor.builder().id(professorId3)
				.firstName("Professor3")
				.lastName("Test3")
				.userName("professor3.test3")
				.salary(40000)
				.build();

		List<Professor> professors = List.of(professor1, professor2, professor3);

		when(professorRepository.findAll()).thenReturn(professors);

		List<Professor> responseList = professorService.findAll();

		assertEquals(professorId1, responseList.get(0).getId());
		assertEquals(professorId2, responseList.get(1).getId());
		assertEquals(professorId3, responseList.get(2).getId());

		assertEquals("Professor1", responseList.get(0).getFirstName());
		assertEquals("Professor2", responseList.get(1).getFirstName());
		assertEquals("Professor3", responseList.get(2).getFirstName());

	}

	@Test
	void givenExistingProfessors_whenFindAllWithPagination_thenItReturnsListOfProfessorsPaginated() {
		String professorId1 = UUID.randomUUID().toString();
		String professorId2 = UUID.randomUUID().toString();
		String professorId3 = UUID.randomUUID().toString();
		String professorId4 = UUID.randomUUID().toString();

		Professor professor1 = Professor.builder()
				.id(professorId1)
				.firstName("Professor1")
				.lastName("Test1")
				.userName("professor1.test1")
				.salary(30000)
				.build();

		Professor professor2 = Professor.builder()
				.id(professorId2)
				.firstName("Professor2")
				.lastName("Test2")
				.userName("professor2.test2")
				.salary(35000)
				.build();

		Professor professor3 = Professor.builder()
				.id(professorId3)
				.firstName("Professor3")
				.lastName("Test3")
				.userName("professor3.test3")
				.salary(40000)
				.build();

		Professor professor4 = Professor.builder()
				.id(professorId4)
				.firstName("Professor4")
				.lastName("Test4")
				.userName("professor4.test4")
				.salary(58000)
				.build();

		List<Professor> professors = List.of(professor1, professor2, professor3, professor4);

		int limit = 2;
		int offset = 2;

		when(professorRepository.findAll(anyInt(), anyInt())).thenReturn(professors.subList(offset, professors.size()));

		List<Professor> responseList = professorService.findAll(limit, offset);

		assertEquals(professorId3, responseList.get(0).getId());
		assertEquals(professorId4, responseList.get(1).getId());

		assertEquals("Professor3", responseList.get(0).getFirstName());
		assertEquals("Professor4", responseList.get(1).getFirstName());
	}

	@Test
	void givenProfessorWithClasses_whenFindClassesByProfessor_thenItReturnsClassList() throws ResourceNotFoundException {
		String professorID = UUID.randomUUID().toString();

		String classID1 = UUID.randomUUID().toString();
		String classID2 = UUID.randomUUID().toString();
		String classID3 = UUID.randomUUID().toString();

		CourseClass class1 = CourseClass.builder()
				.id(classID1)
				.enrolledStudents(20)
				.capacity(30)
				.available(true)
				.build();

		CourseClass class2 = CourseClass.builder()
				.id(classID2)
				.enrolledStudents(30)
				.capacity(30)
				.available(false)
				.build();

		CourseClass class3 = CourseClass.builder()
				.id(classID3)
				.enrolledStudents(10)
				.capacity(22)
				.available(true)
				.build();

		List<CourseClass> classesByProfessor = List.of(class1, class2, class3);

		when(professorRepository.exists(professorID)).thenReturn(true);
		when(professorRepository.findClasses(professorID)).thenReturn(classesByProfessor);

		List<CourseClass> responseList = professorService.findClassesByProfessor(professorID);
		assertEquals(3, responseList.size());
	}

	@Test
	void givenNonExistingProfessor_whenFindClassesByProfessor_thenItThrowsException() {
		String professorID = UUID.randomUUID().toString();
		String nonExistingProfessorID = UUID.randomUUID().toString();

		when(professorRepository.exists(professorID)).thenReturn(true);
		when(professorRepository.exists(nonExistingProfessorID)).thenReturn(false);

		assertDoesNotThrow(() -> professorService.findClassesByProfessor(professorID));
		assertThrows(ResourceNotFoundException.class, () -> professorService.findClassesByProfessor(nonExistingProfessorID));
	}

}