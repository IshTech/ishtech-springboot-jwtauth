package fi.ishtech.springboot.jwtauth.exception;

import java.io.Serial;
import java.util.NoSuchElementException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Custom runtime exception to inform that a user with the given ID doesn't exist.<br>
 * Results in HTTP status <code>404 - Not Found</code>.<br>
 * Extends {@link NoSuchElementException}, so exception handlers of consuming applications that handle
 * {@link NoSuchElementException} still handle it.
 *
 * @author Muneer Ahmed Syed
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class UserNotFoundException extends NoSuchElementException {

	@Serial
	private static final long serialVersionUID = -4012467318934710539L;

	public UserNotFoundException(Long id) {
		super("User (" + id + ") not found");
	}

}
