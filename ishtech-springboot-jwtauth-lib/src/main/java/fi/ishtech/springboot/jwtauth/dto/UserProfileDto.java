package fi.ishtech.springboot.jwtauth.dto;

import java.io.Serial;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

import fi.ishtech.springboot.base.validation.ValidationGroups;
import fi.ishtech.springboot.base.vo.BaseStandardNoIdEntityVo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * DTO for UserProfile entity
 *
 * @author Muneer Ahmed Syed
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class UserProfileDto extends BaseStandardNoIdEntityVo {

	@Serial
	private static final long serialVersionUID = -4206245277207802646L;

	private Long id;

	private String email;

	@NotBlank(groups = ValidationGroups.Update.class)
	private String firstName;

	private String middleName;

	@NotBlank(groups = ValidationGroups.Update.class)
	private String lastName;

	private String nickName;

	private String title;

	private String prefix;

	private String suffix;

	@NotBlank(groups = ValidationGroups.Update.class)
	@Pattern(regexp = "^[a-z]{2}$", message = "defaultLang must be exactly 2 lowercase letters")
	private String defaultLang;

	/**
	 * Combines {@link #firstName}, {@link #middleName} and {@link #lastName} to make fullName
	 *
	 * @return fullName {@link String}
	 */
	public String getFullName() {
		if (middleName != null && !middleName.isBlank()) {
			return String.join(" ", firstName == null ? "" : firstName.strip(), middleName.strip(),
					lastName == null ? "" : lastName.strip());
		} else {
			return String.join(" ", firstName == null ? "" : firstName.strip(),
					lastName == null ? "" : lastName.strip());
		}
	}

}