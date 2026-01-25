package fi.ishtech.springboot.jwtauth.enums;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * {@code enum} for standard user roles
 *
 * @author Muneer Ahmed Syed
 */
public enum StandardUserRoleEnum {

	/**
	 * Administrator role
	 */
	ADMIN,

	/**
	 * Regular user role
	 */
	USER;

	private static final String SCOPE = "SCOPE_";
	private static final String ROLE = "ROLE_";

	/**
	 * Gets the enum by name (ignoring case), or throws if not found.
	 *
	 * @param name {@link String}
	 * @return {@link StandardUserRoleEnum}
	 * @throws IllegalArgumentException if no match
	 */
	public static StandardUserRoleEnum valueOfOrElseThrow(String name) {
		// @formatter:off
		return Arrays.stream(StandardUserRoleEnum.values())
				.filter(e -> e.name().equalsIgnoreCase(name)).findFirst()
				.orElseThrow(() -> new IllegalArgumentException("No enum constant StandardUserRoleEnum." + name));
		// @formatter:on
	}

	/**
	 * Gets the enum by name (ignoring case), or {@code null} if not found.
	 *
	 * @param name {@link String}
	 * @return {@link StandardUserRoleEnum} or {@code null}
	 */
	public static StandardUserRoleEnum valueOfOrElseNull(String name) {
		// @formatter:off
		return Arrays.stream(StandardUserRoleEnum.values())
				.filter(e -> e.name().equalsIgnoreCase(name))
				.findFirst()
				.orElse(null);
		// @formatter:on
	}

	/**
	 * Gets {@code enum}s by names (ignoring case), skipping non-matches.
	 *
	 * @param names {@link List}&lt;{@link String}&gt;
	 * @return {@link List}&lt;{@link StandardUserRoleEnum}&gt;
	 */
	public static List<StandardUserRoleEnum> valueOfOrElseNull(List<String> names) {
		// @formatter:off
		return names.stream()
						.map(name -> Arrays.stream(StandardUserRoleEnum.values())
								.filter(e -> e.name().equalsIgnoreCase(name))
								.findFirst()
								.orElse(null))
						.filter(e -> e != null)
						.collect(Collectors.toList());
		// @formatter:on
	}

	/**
	 * Returns prefixed with "SCOPE_".
	 *
	 * @return {@link String}
	 */
	public String asScope() {
		return SCOPE + name();
	}

	/**
	 * Returns prefixed with "ROLE_".
	 *
	 * @return {@link String}
	 */
	public String asRole() {
		return ROLE + name();
	}

	/**
	 * Returns prefixed with "SCOPE_" for given {@code enum}<br>
	 * or {@code null} if {@code enum} is {@code null}.
	 *
	 * @param standardUserRoleEnum {@link StandardUserRoleEnum}
	 * @return {@link String} or {@code null}
	 */
	public static String asScope(StandardUserRoleEnum standardUserRoleEnum) {
		return standardUserRoleEnum == null ? null : standardUserRoleEnum.asScope();
	}

	/**
	 * Returns prefixed with "ROLE_" for the given {@code enum}<br>
	 * or {@code null} if {@code enum} is {@code null}.
	 *
	 * @param standardUserRoleEnum {@link StandardUserRoleEnum}r
	 * @return {@link String} or {@code null}
	 */
	public static String asRole(StandardUserRoleEnum standardUserRoleEnum) {
		return standardUserRoleEnum == null ? null : standardUserRoleEnum.asRole();
	}

	/**
	 * Returns prefixed with "SCOPE_" for the given {@code enum}s.
	 *
	 * @param standardUserRoleEnums {@link List}&lt;{@link StandardUserRoleEnum}&gt;
	 * @return {@link List}&lt;{@link String}&gt;
	 */
	public static List<String> asScopes(List<StandardUserRoleEnum> standardUserRoleEnums) {
		// @formatter:off
		return standardUserRoleEnums.stream()
				.map(u -> u.asScope())
				.collect(Collectors.toList());
		// @formatter:on
	}

	/**
	 * Returns prefixed with "ROLE_" for the given {@code enum}s.
	 *
	 * @param standardUserRoleEnums {@link List}&lt;{@link StandardUserRoleEnum}&gt;
	 * @return {@link List}&lt;{@link String}&gt;
	 */
	public static List<String> asRoles(List<StandardUserRoleEnum> standardUserRoleEnums) {
		// @formatter:off
		return standardUserRoleEnums.stream()
				.map(u -> u.asRole())
				.collect(Collectors.toList());
		// @formatter:on
	}

}