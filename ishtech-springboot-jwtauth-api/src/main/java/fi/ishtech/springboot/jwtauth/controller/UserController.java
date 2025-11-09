package fi.ishtech.springboot.jwtauth.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fi.ishtech.springboot.jwtauth.dto.UserProfileDto;
import fi.ishtech.springboot.jwtauth.service.AuthInfoService;
import fi.ishtech.springboot.jwtauth.service.UserProfileService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 *
 * @author Muneer Ahmed Syed
 */
@RestController
@RequestMapping
@RequiredArgsConstructor
@Slf4j
public class UserController {

	private final UserProfileService userProfileService;
	private final AuthInfoService authInfoService;

	@GetMapping(path = "/api/v1/users/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasAuthority('ROLE_ADMIN') || #userId == 'me' || #userId == authentication.principal.id")
	public ResponseEntity<UserProfileDto> findUserProfile(
			@Pattern(regexp = "^(me|\\d+)$", message = "Invalid input. Only 'me' or an integer allowed.") @PathVariable("userId") String userId,
			Authentication authentication) {
		log.info("Input userId:{}", userId);

		Long loggedInUserId = authInfoService.getUserId();
		Long inputUserId = "me".equalsIgnoreCase(userId) ? loggedInUserId : Long.valueOf(userId);

		var userProfileDto = userProfileService.findOneByIdAndMapToVoOrElseThrow(inputUserId);

		return ResponseEntity.ok(userProfileDto);
	}

	// @formatter:off
	@PutMapping(path = "/api/v1/users/{userId}",
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("(hasAuthority('ROLE_ADMIN') and (#userProfileDto.id == null or #userId == #userProfileDto.id)) " +
			"or ((#userId == 'me' or #userId == authentication.principal.id) and " +
			"(#userProfileDto.id == null or #authentication.principal.id == #userProfileDto.id))")
	public ResponseEntity<UserProfileDto> updateUserProfile(
			@Pattern(regexp = "^(me|\\d+)$", message = "Invalid input. Only 'me' or an integer allowed.")
			@PathVariable("userId") String userId,
			@Valid @RequestBody UserProfileDto userProfileDto) {
	// @formatter:on
		log.info("Input userId:{}", userId);

		Long loggedInUserId = authInfoService.getUserId();
		log.debug("URL param: {}, request body param: {}, logged in userId: {}", userId, userProfileDto.getId(),
				loggedInUserId);

		if ((authInfoService.isAdmin() && (userProfileDto.getId() == null || userId.equals(userProfileDto.getId())))
				|| (("me".equalsIgnoreCase(userId) || userId.equals(loggedInUserId))
						&& (userProfileDto.getId() == null || loggedInUserId.equals(userProfileDto.getId())))) {
			// ok
			// this is redundant kept here for explanation as Spring-EL in PreAuthorize would take care of it
		} else {
			// forbidden
			log.error("URL param: {}, request body param: {}, logged in userId: {} are NOT matching", userId,
					userProfileDto.getId(), loggedInUserId);
			return ResponseEntity.status(HttpStatus.FORBIDDEN).build(); // "Cannot modify info of other users"
		}

		if (userProfileDto.getId() == null) {
			userProfileDto.setId("me".equalsIgnoreCase(userId) ? loggedInUserId : Long.valueOf(userId));
		}

		UserProfileDto result = userProfileService.updateAndMapToDto(userProfileDto);

		return ResponseEntity.ok(result);
	}

}