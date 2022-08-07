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

	@Test
	void givenValidId_whenFindById_thenReturnUser() {

	}

	@Test
	void givenInvalidId_whenFindById_thenThrowException() {

	}

	@Test
	void givenThereAreUsers_whenFindAll_thenReturnListWithUsers() throws NoUsersException {

	}

	@Test
	void givenThereAreNotUsers_whenFindAll_thenThrowException() {

	}

	@Test
	void givenValidUserData_whenSave_thenReturnOne() throws AlreadyExistingUserNameException, EmptyUserNameException {

	}

	@Test
	void givenEmptyUserName_whenSave_thenThrowException() {

	}

	@Test
	void givenExistingUserName_whenSave_thenThrowException() {

	}

	@Test
	void givenExistingUser_whenUpdate_thenReturnOne() throws EmptyUserNameException {

	}

	@Test
	void givenNonExistingUser_whenUpdate_thenThrowException() {

	}

	@Test
	void givenEmptyName_whenUpdate_thenThrowException() {

	}

	@Test
	void givenValidId_whenDeleteById_thenReturnOne() {

	}

	@Test
	void givenNonExistingId_whenDeleteById_thenThrowException() {

	}


}