package fi.ishtech.springboot.jwtauth.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import fi.ishtech.springboot.jwtauth.entity.User;

/**
 * {@link Repository} for entity {@link User}
 *
 * @author Muneer Ahmed Syed
 */
public interface UserRepo extends JpaRepository<User, Long> {

	/**
	 * Checks if a {@link User} exists with the given username.
	 *
	 * @param username {@link String}
	 * @return {@code true} if user exists, {@code false} otherwise
	 */
	boolean existsByUsername(String username);

	/**
	 * Checks if a user {@link User} with the given email.
	 *
	 * @param email {@link String}
	 * @return {@code true} if user exists, {@code false} otherwise
	 */
	boolean existsByEmail(String email);

	/**
	 * Finds a {@link User} by username.
	 *
	 * @param username {@link String}
	 * @return {@link Optional}&lt;{@link User}&gt;
	 */
	Optional<User> findOneByUsername(String username);

	/**
	 * Finds a {@link User} by email address.
	 *
	 * @param email {@link String}
	 * @return {@link Optional}&lt;{@link User}&gt;
	 */
	Optional<User> findOneByEmail(String email);

	/**
	 * Finds a {@link User} by password reset token.
	 *
	 * @param token password reset token {@link String}
	 * @return {@link Optional}&lt;{@link User}&gt;
	 */
	Optional<User> findOneByPasswordResetToken(String token);

	/**
	 * Retrieves password hash for a {@link User} by ID.
	 *
	 * @param userId {@link String}
	 * @return password hash {@link String}
	 */
	@Query("SELECT u.passwordHash FROM User u WHERE u.id = :userId")
	String findPasswordHashById(Long userId);

	/**
	 * Updates {@link User}'s password.
	 *
	 * @param userId      {@link String}
	 * @param newPassword new password hash {@link String}
	 */
	@Modifying
	@Query("UPDATE User u SET u.passwordHash = :newPassword WHERE u.id = :userId")
	void updatePassword(Long userId, String newPassword);

}