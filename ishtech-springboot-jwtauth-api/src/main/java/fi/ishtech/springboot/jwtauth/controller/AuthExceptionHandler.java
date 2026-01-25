package fi.ishtech.springboot.jwtauth.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import fi.ishtech.springboot.jwtauth.exception.UsernameAlreadyExistsException;

import lombok.extern.slf4j.Slf4j;

/**
 * Custom exception handler for auth requests
 *
 * @author Muneer Ahmed Syed
 */
//@ControllerAdvice
@Slf4j
public class AuthExceptionHandler {

	/**
	 * Handles exception returns response with appropriate HTTP code
	 *
	 * @param ex {@link UsernameAlreadyExistsException}
	 * @return {@link ResponseEntity}&lt;{@link ErrorResponse}&gt;
	 */
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(UsernameAlreadyExistsException.class)
	public ResponseEntity<ErrorResponse> handleUsernameAlreadyExistsException(UsernameAlreadyExistsException ex) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.body(ErrorResponse.create(ex, HttpStatus.BAD_REQUEST, ex.getMessage()));
	}

	/**
	 * Handles exception returns response with appropriate HTTP code
	 *
	 * @param ex {@link BadCredentialsException}
	 * @return {@link ResponseEntity}&lt;{@link ErrorResponse}&gt;
	 */
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	@ExceptionHandler(BadCredentialsException.class)
	public ResponseEntity<ErrorResponse> handleBadCredentialsException(BadCredentialsException ex) {
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
				.body(ErrorResponse.create(ex, HttpStatus.UNAUTHORIZED, ex.getMessage()));
	}

}