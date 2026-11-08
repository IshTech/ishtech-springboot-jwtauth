package fi.ishtech.springboot.jwtauth.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.groups.Default;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fi.ishtech.springboot.base.validation.ValidationGroups;
import fi.ishtech.springboot.jwtauth.dto.UserProfileDto;
import fi.ishtech.springboot.jwtauth.payload.params.UserProfileFilterParams;
import fi.ishtech.springboot.jwtauth.service.AuthInfoService;
import fi.ishtech.springboot.jwtauth.service.UserProfileService;
import fi.ishtech.springboot.jwtauth.spec.UserProfileSpec;

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

	/**
	 * Finds UserProfile(s) by search filters and pagination
	 *
	 * @param params   - {@link UserProfileFilterParams}
	 * @param pageable - {@link Pageable}
	 * @return {@link ResponseEntity}&lt;{@link Page}&lt;{@link UserProfileDto}&gt;&gt;
	 */
	@GetMapping(path = "/api/v1/users", produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
	public ResponseEntity<Page<UserProfileDto>> searchUserProfiles(@Valid UserProfileFilterParams params,
			Pageable pageable) {
		log.trace("UserProfileFilterParams:{}", params);

		UserProfileSpec userProfileSpec = new UserProfileSpec(params);

		Page<UserProfileDto> result = userProfileService.findAllAndMapToVo(userProfileSpec, pageable);

		return ResponseEntity.ok(result);
	}

	/**
	 * Finds UserProfile by userId or for current user
	 *
	 * @param userId user ID or me
	 * @return {@link ResponseEntity}&lt;{@link UserProfileDto}&gt;
	 */
	// @formatter:off
	@GetMapping(path = "/api/v1/users/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasAuthority('ROLE_ADMIN') || #userId == 'me'"
			+ " || T(java.lang.Long).valueOf(#userId) == principal.id")
	public ResponseEntity<UserProfileDto> findUserProfile(
			@Pattern(regexp = "^(me|\\d+)$", message = "Invalid input. Only 'me' or an integer allowed.")
				@PathVariable("userId") String userId) {
		// @formatter:on
		log.info("Input userId:{}", userId);

		Long loggedInUserId = authInfoService.getUserId();
		log.debug("URL param: {}, logged in userId: {}", userId, loggedInUserId);

		Long inputUserId = "me".equalsIgnoreCase(userId) ? loggedInUserId : Long.valueOf(userId);

		var userProfileDto = userProfileService.findOneByIdAndMapToVoOrElseThrow(inputUserId);

		return ResponseEntity.ok(userProfileDto);
	}

	/**
	 * Replaces UserProfile by userId or for current user.
	 * <p>
	 * This is an HTTP {@code PUT}, so the request body must carry the complete resource. A property that is absent from
	 * the body is cleared, and a property the resource cannot be without is required by the
	 * {@link ValidationGroups.Update} validation group, so omitting one gives {@code 400 Bad Request}. To change only
	 * some of the properties, use {@link #patchUserProfile(String, UserProfileDto)}.
	 *
	 * @param userId         user ID or <code>me</code>
	 * @param userProfileDto {@link UserProfileDto}, the complete resource
	 *
	 * @return {@link ResponseEntity}&lt;{@link UserProfileDto}&gt;
	 */
	// @formatter:off
	@PutMapping(path = "/api/v1/users/{userId}",
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize(
		    "('me' == #userId && (#userProfileDto.id == null || #userProfileDto.id == principal.id))"
		    + " || ("
		    + "    (hasAuthority('ROLE_ADMIN') || ('me' != #userId && #userId == principal.id.toString()))"
		    + "    && (#userProfileDto.id == null || #userId == #userProfileDto.id.toString())"
		    + " )"
		)
	public ResponseEntity<UserProfileDto> updateUserProfile(
			@Pattern(regexp = "^(me|\\d+)$", message = "Invalid input. Only 'me' or an integer allowed.")
			@PathVariable("userId") String userId,
			@Validated({ Default.class, ValidationGroups.Update.class })
				@RequestBody UserProfileDto userProfileDto) {
	// @formatter:on
		log.info("Input userId:{}", userId);

		if (!isPermittedAndResolveId(userId, userProfileDto)) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
		}

		UserProfileDto result = userProfileService.updateAndMapToDto(userProfileDto);

		return ResponseEntity.ok(result);
	}

	/**
	 * Updates the supplied properties of UserProfile by userId or for current user.
	 * <p>
	 * This is an HTTP {@code PATCH}, so the request body may carry any subset of the resource. A property that is
	 * absent from the body is left as stored. Because absence and {@code null} cannot be told apart in the request
	 * body, this endpoint cannot clear a property; use {@link #updateUserProfile(String, UserProfileDto)} for that.
	 * <p>
	 * The body is validated against the {@link ValidationGroups.Patch} validation group, which holds no mandatory
	 * property, so every property stays optional here.
	 *
	 * @param userId         user ID or <code>me</code>
	 * @param userProfileDto {@link UserProfileDto}, the properties to change
	 *
	 * @return {@link ResponseEntity}&lt;{@link UserProfileDto}&gt;
	 */
	// @formatter:off
	@PatchMapping(path = "/api/v1/users/{userId}",
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize(
		    "('me' == #userId && (#userProfileDto.id == null || #userProfileDto.id == principal.id))"
		    + " || ("
		    + "    (hasAuthority('ROLE_ADMIN') || ('me' != #userId && #userId == principal.id.toString()))"
		    + "    && (#userProfileDto.id == null || #userId == #userProfileDto.id.toString())"
		    + " )"
		)
	public ResponseEntity<UserProfileDto> patchUserProfile(
			@Pattern(regexp = "^(me|\\d+)$", message = "Invalid input. Only 'me' or an integer allowed.")
			@PathVariable("userId") String userId,
			@Validated({ Default.class, ValidationGroups.Patch.class })
				@RequestBody UserProfileDto userProfileDto) {
	// @formatter:on
		log.info("Input userId:{}", userId);

		if (!isPermittedAndResolveId(userId, userProfileDto)) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
		}

		UserProfileDto result = userProfileService.patchAndMapToDto(userProfileDto);

		return ResponseEntity.ok(result);
	}

	/**
	 * Checks that the caller may modify the UserProfile addressed by {@code userId}, and fills in the id of
	 * {@code userProfileDto} from the URL when the request body leaves it out.
	 * <p>
	 * The check itself is redundant, as the Spring-EL in {@code @PreAuthorize} on the calling endpoint already rejects
	 * the same requests. It is kept for explanation, and is shared by the {@code PUT} and the {@code PATCH} endpoint so
	 * that the two cannot drift apart.
	 *
	 * @param userId         user ID or <code>me</code> from the URL
	 * @param userProfileDto {@link UserProfileDto} from the request body
	 *
	 * @return {@code true} when the caller is permitted, or else {@code false}
	 */
	private boolean isPermittedAndResolveId(String userId, UserProfileDto userProfileDto) {
		Long loggedInUserId = authInfoService.getUserId();
		log.debug("URL param: {}, request body param: {}, logged in userId: {}", userId, userProfileDto.getId(),
				loggedInUserId);

		// @formatter:off
		if (
			// Part 1: User is using "me" in URL
			("me".equals(userId) && (userProfileDto.getId() == null || userProfileDto.getId().equals(loggedInUserId)))
			||
			// Part 2: Admin OR numeric userId matching principal
			(
				(authInfoService.isAdmin() || (!"me".equals(userId) && userId.equals(loggedInUserId.toString())))
				&&
				(userProfileDto.getId() == null || userId.equals(userProfileDto.getId().toString()))
			)
		) {
		// @formatter:on
			// ok
			// this is redundant kept here for explanation as Spring-EL in PreAuthorize would take care of it
		} else {
			// forbidden
			// Cannot modify info of other users
			log.error("URL param: {}, request body param: {}, logged in userId: {} are NOT matching", userId,
					userProfileDto.getId(), loggedInUserId);
			return false;
		}

		if (userProfileDto.getId() == null) {
			userProfileDto.setId("me".equalsIgnoreCase(userId) ? loggedInUserId : Long.valueOf(userId));
		}

		return true;
	}

}