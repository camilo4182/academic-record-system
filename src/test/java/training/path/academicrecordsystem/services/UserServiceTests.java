package training.path.academicrecordsystem.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import training.path.academicrecordsystem.model.User;
import training.path.academicrecordsystem.repositories.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
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
	void givenInvalidId_whenFindById_thenRaiseException() {
		when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

		assertThrows(NoSuchElementException.class, () -> userService.findById(100L));
	}

	@Test
	void givenThereAreUsers_whenFindAll_thenReturnListWithUsers() {
		List<User> fakeList = new ArrayList<>();
		fakeList.add(new User(1L, "Juan"));
		fakeList.add(new User(2L, "Ana"));
		fakeList.add(new User(3L, "Carlos"));

		when(userRepository.findAll()).thenReturn(fakeList);

		List<User> foundUsers = userService.findAll();
		assertEquals(3, foundUsers.size());
		assertEquals("Juan", foundUsers.get(0).getName());
		assertEquals("Ana", foundUsers.get(1).getName());
		assertEquals("Carlos", foundUsers.get(2).getName());
	}

	@Test
	void givenThereAreNotUsers_whenFindAll_thenRaiseException() {
		List<User> emptyUsersList = new ArrayList<>();

		when(userRepository.findAll()).thenReturn(emptyUsersList);

		assertThrows(Exception.class, () -> userService.findAll());
	}

}
