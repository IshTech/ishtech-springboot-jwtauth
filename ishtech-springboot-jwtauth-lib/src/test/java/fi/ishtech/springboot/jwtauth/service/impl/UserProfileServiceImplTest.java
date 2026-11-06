package fi.ishtech.springboot.jwtauth.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import fi.ishtech.springboot.jwtauth.dto.UserProfileDto;
import fi.ishtech.springboot.jwtauth.exception.UserNotFoundException;
import fi.ishtech.springboot.jwtauth.mapper.UserProfileMapper;
import fi.ishtech.springboot.jwtauth.repo.UserProfileRepo;

/**
 * Test class for {@link UserProfileServiceImpl}
 *
 * @author Muneer Ahmed Syed
 */
@ExtendWith(MockitoExtension.class)
public class UserProfileServiceImplTest {

	@Mock
	private UserProfileRepo userProfileRepo;

	@Mock
	private UserProfileMapper userProfileMapper;

	@InjectMocks
	private UserProfileServiceImpl userProfileService;

	@Test
	void testFindOneByIdAndMapToVoOrElseThrowForNonExistentId() {
		when(userProfileRepo.findById(9999L)).thenReturn(Optional.empty());

		UserNotFoundException ex = assertThrows(UserNotFoundException.class,
				() -> userProfileService.findOneByIdAndMapToVoOrElseThrow(9999L));
		assertEquals("User (9999) not found", ex.getMessage());
	}

	@Test
	void testUpdateAndMapToDtoForNonExistentId() {
		when(userProfileRepo.findById(9999L)).thenReturn(Optional.empty());

		UserProfileDto userProfileDto = new UserProfileDto();
		userProfileDto.setId(9999L);
		userProfileDto.setFirstName("Testi");
		userProfileDto.setLastName("Besti");

		UserNotFoundException ex = assertThrows(UserNotFoundException.class,
				() -> userProfileService.updateAndMapToDto(userProfileDto));
		assertEquals("User (9999) not found", ex.getMessage());

		verify(userProfileRepo, never()).save(any());
	}

}
