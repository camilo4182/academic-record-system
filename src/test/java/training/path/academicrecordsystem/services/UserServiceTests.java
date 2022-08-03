package training.path.academicrecordsystem.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import training.path.academicrecordsystem.exceptions.AlreadyExistingUserNameException;
import training.path.academicrecordsystem.exceptions.EmptyUserNameException;
import training.path.academicrecordsystem.exceptions.NoUsersException;
import training.path.academicrecordsystem.model.User;
import training.path.academicrecordsystem.repositories.interfaces.UserRepository;
import training.path.academicrecordsystem.services.implementations.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class UserServiceTests {

	@Mock
	UserRepository userRepository;

	@InjectMocks
    UserService userService;

	@BeforeEach
	void setupUserService() {
		//MockitoAnnotations.openMocks(this);
	}

	@Test
	void givenValidId_whenFindById_thenReturnUser() {
		User fakeUser = new User();
		fakeUser.setId(1);
		fakeUser.setName("Juan");

		when(userRepository.findById(anyLong())).thenReturn(Optional.of(fakeUser));

		assertDoesNotThrow(() -> userService.findById(1L));
		User foundUser = userService.findById(1L);
		assertNotNull(foundUser);
		assertEquals("Juan", foundUser.getName());
	}

	@Test
	void givenInvalidId_whenFindById_thenThrowException() {
		when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

		assertThrows(NoSuchElementException.class, () -> userService.findById(100L));
	}

	@Test
	void givenThereAreUsers_whenFindAll_thenReturnListWithUsers() throws NoUsersException {
		List<User> fakeList = new ArrayList<>();
		fakeList.add(new User(1L, "Juan"));
		fakeList.add(new User(2L, "Ana"));
		fakeList.add(new User(3L, "Carlos"));

		when(userRepository.findAll()).thenReturn(fakeList);

		assertDoesNotThrow(() -> userService.findAll());
		List<User> foundUsers = userService.findAll();
		assertEquals(3, foundUsers.size());
		assertEquals("Juan", foundUsers.get(0).getName());
		assertEquals("Ana", foundUsers.get(1).getName());
		assertEquals("Carlos", foundUsers.get(2).getName());
	}

	@Test
	void givenThereAreNotUsers_whenFindAll_thenThrowException() {
		List<User> emptyUsersList = new ArrayList<>();

		when(userRepository.findAll()).thenReturn(emptyUsersList);

		assertThrows(NoUsersException.class, () -> userService.findAll());
	}

	@Test
	void givenValidUserData_whenSave_thenReturnOne() throws AlreadyExistingUserNameException, EmptyUserNameException {
		User user = new User();
		user.setId(1L);
		user.setName("Ana");

		when(userRepository.save(any())).thenReturn(1);

		assertEquals(1, userService.save(user));
	}

	@Test
	void givenEmptyUserName_whenSave_thenThrowException() {
		User user = new User();
		user.setId(1L);
		user.setName("");

		when(userRepository.save(any())).thenReturn(0);

		assertThrows(EmptyUserNameException.class, () -> userService.save(user));
	}

	@Test
	void givenExistingUserName_whenSave_thenThrowException() {
		User user = new User();
		user.setId(1L);
		user.setName("Juan");

		when(userRepository.findByName(anyString())).thenReturn(Optional.of(user));
		when(userRepository.save(any())).thenReturn(0);

		assertThrows(AlreadyExistingUserNameException.class, () -> userService.save(user));
	}

	@Test
	void givenExistingUser_whenUpdate_thenReturnOne() throws EmptyUserNameException {
		User user = new User();
		user.setName("Carlos");

		when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
		when(userRepository.update(anyLong(), any())).thenReturn(1);

		assertEquals(1, userService.update(1L, user));
	}

	@Test
	void givenNonExistingUser_whenUpdate_thenThrowException() {
		User user = new User();
		user.setName("Null");

		when(userRepository.findById(anyLong())).thenReturn(Optional.empty());
		when(userRepository.update(anyLong(), any())).thenReturn(0);

		assertThrows(NoSuchElementException.class, () -> userService.update(10000L, user));
	}

	@Test
	void givenEmptyName_whenUpdate_thenThrowException() {
		User user = new User();
		user.setId(1L);
		user.setName("");

		when(userRepository.update(anyLong(), any())).thenReturn(1);

		assertThrows(EmptyUserNameException.class, () -> userService.update(1L, user));
	}

	@Test
	void givenValidId_whenDeleteById_thenReturnOne() {
		User user = new User();
		user.setId(1L);
		user.setName("Jorge");

		when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
		when(userRepository.deleteById(anyLong())).thenReturn(1);

		assertEquals(1, userService.deleteById(1L));
	}

	@Test
	void givenNonExistingId_whenDeleteById_thenThrowException() {
		when(userRepository.findById(anyLong())).thenReturn(Optional.empty());
		when(userRepository.deleteById(anyLong())).thenReturn(0);

		assertThrows(NoSuchElementException.class, () -> userService.deleteById(100000L));
	}

}
