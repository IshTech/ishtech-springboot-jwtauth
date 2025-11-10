package fi.ishtech.springboot.jwtauth.service.impl;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import fi.ishtech.springboot.jwtauth.enums.StandardUserRoleEnum;
import fi.ishtech.springboot.jwtauth.service.AuthInfoService;
import fi.ishtech.springboot.jwtauth.userdetails.UserDetailsImpl;

import lombok.extern.slf4j.Slf4j;

/**
 *
 * @author Muneer Ahmed Syed
 */
@Service
@Slf4j
public class AuthInfoServiceImpl implements AuthInfoService {

	private Authentication getAuthentication() {
		var authentication = SecurityContextHolder.getContext().getAuthentication();

		if (authentication == null) {
			log.error("Authentication is null");
			throw new HttpClientErrorException(HttpStatus.UNAUTHORIZED);
		}

		return authentication;
	}

	private UserDetailsImpl getUserDetails() {
		var principal = getAuthentication().getPrincipal();

		if (principal instanceof String) {
			// principal = anonymousUser
			log.info("principal:{}", principal);
			return null;
		} else if (!(principal instanceof UserDetailsImpl)) {
			log.error("Principal ({}) is not UserDetailsImpl type", principal.getClass());
			throw new HttpClientErrorException(HttpStatus.UNPROCESSABLE_ENTITY);
		}

		return (UserDetailsImpl) principal;
	}

	@Override
	public Long getUserId() {
		UserDetailsImpl userDetails = getUserDetails();

		return userDetails == null ? null : userDetails.getId();
	}

	@Override
	public String getUsername() {
		UserDetailsImpl userDetails = getUserDetails();

		return userDetails == null ? null : userDetails.getUsername();
	}

	@Override
	public String getEmail() {
		UserDetailsImpl userDetails = getUserDetails();

		return userDetails == null ? null : userDetails.getEmail();
	}

	@Override
	public boolean isAdmin() {
		return hasAnyAuthoritiess(StandardUserRoleEnum.ADMIN.asRole());
	}

	private boolean hasAnyAuthoritiess(String... roles) {
		return hasAnyAuthoritiess(Arrays.asList(roles));
	}

	private boolean hasAnyAuthoritiess(List<String> authorities) {
		// @formatter:off
		return getUserDetails()
				.getAuthorities()
				.stream()
				.map(GrantedAuthority::getAuthority)
				.anyMatch(authority -> authorities.contains(authority));
		// @formatter:on
	}

}