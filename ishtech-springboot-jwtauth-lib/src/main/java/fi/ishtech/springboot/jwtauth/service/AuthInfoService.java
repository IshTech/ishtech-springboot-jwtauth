package fi.ishtech.springboot.jwtauth.service;

/**
 * Service interface for authentication / autherization related operations.
 *
 * @author Muneer Ahmed Syed
 */
public interface AuthInfoService {

	/**
	 * Retrieves the ID of the authenticated user, or {@code null} if not authenticated.
	 *
	 * @return the user ID or {@code null}
	 */
	Long getUserId();

	/**
	 * Retrieves the username of the authenticated user, or {@code null} if not authenticated.
	 *
	 * @return the username or {@code null}
	 */
	String getUsername();

	/**
	 * Retrieves the email of the authenticated user, or {@code null} if not authenticated.
	 *
	 * @return the email or {@code null}
	 */
	String getEmail();

	/**
	 * Checks if the authenticated user has admin privileges.
	 *
	 * @return {@code true} if the user is an admin, {@code false} otherwise
	 */
	boolean isAdmin();

}