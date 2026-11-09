package fi.ishtech.springboot.jwtauth.service;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import fi.ishtech.springboot.base.service.BaseStandardNoIdService;
import fi.ishtech.springboot.jwtauth.dto.SignupDto;
import fi.ishtech.springboot.jwtauth.dto.UserProfileDto;
import fi.ishtech.springboot.jwtauth.entity.UserProfile;
import fi.ishtech.springboot.jwtauth.spec.UserProfileSpec;

/**
 * Service interface for user profile related operations.
 *
 * @author Muneer Ahmed Syed
 */
public interface UserProfileService extends BaseStandardNoIdService<UserProfile, UserProfileDto, Long> {

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
	UserProfileDto findOneByIdAndMapToVoOrElseThrow(Long id);

	/**
	 * Search and find UserProfile(s) by Specification and pageniate them
	 *
	 * @param spec     {@link UserProfileSpec}
	 * @param pageable {@link Pageable}
	 * @return {@link Page}&lt;{@link UserProfileDto}&gt;
	 */
	Page<UserProfileDto> findAllAndMapToVo(UserProfileSpec spec, Pageable pageable);

	/**
	 * Finds by id and updates the entity in the DB and returns updated DTO
	 * <p>
	 * Expects the full DTO as input
	 * <p>
	 * {@code isActive} missing or {@code null} in the DTO is ignored, preserving the existing value in the DB
	 * <p>
	 * An attribute missing or {@code null} (except {@code id}) in the DTO is overwritten in the DB as {@code null}
	 * <p>
	 * Used by the {@code PUT} API, which validates with
	 * {@link fi.ishtech.springboot.base.validation.ValidationGroups.Update ValidationGroups.Update}
	 *
	 * @param userProfileDto complete input UserProfile
	 * @return {@link UserProfileDto}
	 *
	 * @see #patchAndMapToDto(UserProfileDto)
	 */
	UserProfileDto updateAndMapToDto(UserProfileDto userProfileDto);

	/**
	 * Finds by id and updates only the supplied attributes of the entity in the DB and returns updated DTO
	 * <p>
	 * {@code isActive} missing or {@code null} in the DTO is ignored, preserving the existing value in the DB
	 * <p>
	 * An attribute missing or {@code null} in the DTO is ignored, preserving the existing value in the DB
	 * <p>
	 * Used by the {@code PATCH} API
	 *
	 * @param userProfileDto input changes to UserProfile
	 * @return {@link UserProfileDto}
	 *
	 * @see #updateAndMapToDto(UserProfileDto)
	 */
	UserProfileDto patchAndMapToDto(UserProfileDto userProfileDto);

}