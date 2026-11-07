package fi.ishtech.springboot.jwtauth.dto;

import java.io.Serial;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

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

	/**
	 * Validation group for a full replacement of the resource, that is an HTTP {@code PUT}.
	 * <p>
	 * A {@code PUT} carries the complete resource, so a property that is absent from the body is a deliberate
	 * instruction to clear it. Constraints in this group therefore require every property that the resource cannot be
	 * without, so that such a request is rejected with {@code 400 Bad Request} instead of clearing a column that
	 * {@link fi.ishtech.springboot.jwtauth.entity.UserProfile} declares as non-null.
	 * <p>
	 * An HTTP {@code PATCH} is validated without this group, so the same properties stay optional there.
	 */
	public interface OnReplace {
	}

	private Long id;

	private String email;

	@NotBlank(groups = OnReplace.class)
	private String firstName;

	private String middleName;

	@NotBlank(groups = OnReplace.class)
	private String lastName;

	private String nickName;

	private String title;

	private String prefix;

	private String suffix;

	@NotBlank(groups = OnReplace.class)
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