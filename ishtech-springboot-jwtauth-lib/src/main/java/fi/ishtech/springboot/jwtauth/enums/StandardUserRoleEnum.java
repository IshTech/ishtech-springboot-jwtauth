package fi.ishtech.springboot.jwtauth.enums;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author Muneer Ahmed Syed
 */
public enum StandardUserRoleEnum {

	// @formatter:off
	ADMIN,
	USER;
	// @formatter:on

	private static final String SCOPE = "SCOPE_";
	private static final String ROLE = "ROLE_";

	public static StandardUserRoleEnum valueOfOrElseThrow(String name) {
		// @formatter:off
		return Arrays.stream(StandardUserRoleEnum.values())
				.filter(e -> e.name().equalsIgnoreCase(name)).findFirst()
				.orElseThrow(() -> new IllegalArgumentException("No enum constant StandardUserRoleEnum." + name));
		// @formatter:on
	}

	public static StandardUserRoleEnum valueOfOrElseNull(String name) {
		// @formatter:off
		return Arrays.stream(StandardUserRoleEnum.values())
				.filter(e -> e.name().equalsIgnoreCase(name))
				.findFirst()
				.orElse(null);
		// @formatter:on
	}

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

	public String asScope() {
		return SCOPE + name();
	}

	public String asRole() {
		return ROLE + name();
	}

	public static String asScope(StandardUserRoleEnum standardUserRoleEnum) {
		return standardUserRoleEnum == null ? null : standardUserRoleEnum.asScope();
	}

	public static String asRole(StandardUserRoleEnum standardUserRoleEnum) {
		return standardUserRoleEnum == null ? null : standardUserRoleEnum.asRole();
	}

	public static List<String> asScopes(List<StandardUserRoleEnum> standardUserRoleEnums) {
		// @formatter:off
		return standardUserRoleEnums.stream()
				.map(u -> u.asScope())
				.collect(Collectors.toList());
		// @formatter:on
	}

	public static List<String> asRoles(List<StandardUserRoleEnum> standardUserRoleEnums) {
		// @formatter:off
		return standardUserRoleEnums.stream()
				.map(u -> u.asRole())
				.collect(Collectors.toList());
		// @formatter:on
	}

}