package fi.ishtech.springboot.jwtauth.exception;

/**
 *
 * @author Muneer Ahmed Syed
 */
public class UsernameAlreadyExistsException extends RuntimeException {

	private static final long serialVersionUID = 2588985961348598620L;

	public UsernameAlreadyExistsException() {
		super("Username is already in use");
	}

}