package fi.ishtech.springboot.jwtauth.exception;

import java.io.Serial;

/**
 *
 * @author Muneer Ahmed Syed
 */
public class UsernameAlreadyExistsException extends RuntimeException {

	@Serial
	private static final long serialVersionUID = 2588985961348598620L;

	public UsernameAlreadyExistsException() {
		super("Username is already in use");
	}

}