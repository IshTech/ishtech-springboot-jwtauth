package fi.ishtech.springboot.jwtauth.userdetails;

import java.io.Serial;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Implementation of {@link UserDetails}
 *
 * @author Muneer Ahmed Syed
 */
@Getter
@Setter(lombok.AccessLevel.PRIVATE)
@NoArgsConstructor
public class UserDetailsImpl implements UserDetails {

	@Serial
	private static final long serialVersionUID = 8045685643481477217L;

	private static final String ROLE_PREFIX = "ROLE_";

	private Long id;

	private String username;

	private String email;

	@ToString.Exclude
	@JsonIgnore
	private String password;

	private Collection<? extends GrantedAuthority> authorities;

	private boolean isEnabled;

	private String fullName;

	private String lang;

	/**
	 * Creates a UserDetails instance from user data.
	 *
	 * @param id            user identifier
	 * @param username      login username
	 * @param email         user email address
	 * @param password      encrypted password
	 * @param isEnabled     whether user is active
	 * @param userRoleNames list of role names
	 * @param fullName      user's full name
	 * @param lang          user's language preference
	 * @return configured UserDetails instance
	 */
	public static UserDetails of(Long id, String username, String email, String password, boolean isEnabled,
			List<String> userRoleNames, String fullName, String lang) {
		UserDetailsImpl userDetails = new UserDetailsImpl();

		userDetails.setId(id);

		userDetails.setUsername(username);
		userDetails.setEmail(email);
		userDetails.setPassword(password);

		userDetails.setEnabled(isEnabled);

		userDetails.setAuthorities(convertToAuthorities(userRoleNames));

		userDetails.setFullName(fullName);
		userDetails.setLang(lang);

		return userDetails;
	}

	/**
	 * Returns user authorities as scope strings.
	 *
	 * @return list of authority scope strings
	 */
	@JsonIgnore
	public List<String> getScopes() {
		return getAuthorities().stream().map(item -> item.getAuthority()).toList();
	}

	/**
	 * Returns role names without the ROLE_ prefix.
	 *
	 * @return list of role names
	 */
	@JsonIgnore
	public List<String> getRoleNames() {
		// @formatter:off
		return getAuthorities().stream()
				.map(GrantedAuthority::getAuthority)
				.map(role -> role.startsWith(ROLE_PREFIX) ? role.substring(5) : role)
				.toList();
		// @formatter:on
	}

	/**
	 * Converts role names to GrantedAuthority objects with ROLE_ prefix.
	 *
	 * @param roleNames list of role names
	 * @return list of SimpleGrantedAuthority objects
	 */
	private static List<SimpleGrantedAuthority> convertToAuthorities(List<String> roleNames) {
		// @formatter:off
		return roleNames.stream()
				.filter(Objects::nonNull)
				.map(String::trim)
				.filter(name -> !name.isEmpty())
				.map(name -> name.toUpperCase().startsWith(ROLE_PREFIX)
						? name.toUpperCase()
						: ROLE_PREFIX + name.toUpperCase())
				.map(SimpleGrantedAuthority::new)
				.toList();
		// @formatter:on
	}

}