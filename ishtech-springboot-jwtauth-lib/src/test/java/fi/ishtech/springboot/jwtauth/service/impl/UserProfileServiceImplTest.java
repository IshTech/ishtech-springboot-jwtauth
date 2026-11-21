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
import fi.ishtech.springboot.jwtauth.entity.UserProfile;
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

	/**
	 * Builds a dto carrying only the properties that a rename supplies.
	 *
	 * @param id the user ID
	 * @return {@link UserProfileDto}
	 */
	private UserProfileDto dtoWithId(Long id) {
		UserProfileDto userProfileDto = new UserProfileDto();
		userProfileDto.setId(id);
		userProfileDto.setFirstName("Testi");
		userProfileDto.setLastName("Besti");

		return userProfileDto;
	}

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

		UserProfileDto userProfileDto = dtoWithId(9999L);

		UserNotFoundException ex = assertThrows(UserNotFoundException.class,
				() -> userProfileService.updateAndMapToDto(userProfileDto));
		assertEquals("User (9999) not found", ex.getMessage());

		verify(userProfileRepo, never()).save(any());
	}

	@Test
	void testPatchAndMapToDtoForNonExistentId() {
		when(userProfileRepo.findById(9999L)).thenReturn(Optional.empty());

		UserProfileDto userProfileDto = dtoWithId(9999L);

		UserNotFoundException ex = assertThrows(UserNotFoundException.class,
				() -> userProfileService.patchAndMapToDto(userProfileDto));
		assertEquals("User (9999) not found", ex.getMessage());

		verify(userProfileRepo, never()).save(any());
	}

	@Test
	void testUpdateAndMapToDtoReplacesViaToExistingEntity() {
		UserProfile existing = new UserProfile();
		existing.setId(100L);

		when(userProfileRepo.findById(100L)).thenReturn(Optional.of(existing));
		when(userProfileRepo.save(existing)).thenReturn(existing);

		UserProfileDto userProfileDto = dtoWithId(100L);

		userProfileService.updateAndMapToDto(userProfileDto);

		verify(userProfileMapper).toExistingEntity(userProfileDto, existing);
		verify(userProfileMapper, never()).patchExistingEntity(any(), any());
		verify(userProfileRepo).save(existing);
	}

	@Test
	void testPatchAndMapToDtoUpdatesViaPatchExistingEntity() {
		UserProfile existing = new UserProfile();
		existing.setId(100L);

		when(userProfileRepo.findById(100L)).thenReturn(Optional.of(existing));
		when(userProfileRepo.save(existing)).thenReturn(existing);

		UserProfileDto userProfileDto = dtoWithId(100L);

		userProfileService.patchAndMapToDto(userProfileDto);

		verify(userProfileMapper).patchExistingEntity(userProfileDto, existing);
		verify(userProfileMapper, never()).toExistingEntity(any(), any());
		verify(userProfileRepo).save(existing);
	}

}
