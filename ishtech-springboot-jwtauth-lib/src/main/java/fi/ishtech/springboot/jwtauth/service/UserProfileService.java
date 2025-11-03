package fi.ishtech.springboot.jwtauth.service;

import fi.ishtech.springboot.jwtauth.dto.SignupDto;
import fi.ishtech.springboot.jwtauth.dto.UserProfileDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

/**
 * Service interface for user profile related operations.
 *
 * @author Muneer Ahmed Syed
 * 
 */
public interface UserProfileService {

	/**
	 * Creates a user profile from the provided signup data.
	 *
	 * @param userId    the ID of the user
	 * @param signupDto the signup data transfer object
	 * @return UserProfileDto
	 */
	UserProfileDto create(@NotNull Long userId, @NotNull @Valid SignupDto signupDto);

	/**
	 * Find by id and maps to dto
	 *
	 * @param id the ID of the user
	 * @return {@link UserProfileDto}
	 */
	UserProfileDto findByIdAndMapToDto(Long id);

	/**
	 * Finds by id and updates the entity in DB and return updated dto
	 *
	 * @param userProfileDto input changes to UserProfile
	 * @return {@link UserProfileDto}
	 */
	UserProfileDto updateAndMapToDto(UserProfileDto userProfileDto);

}