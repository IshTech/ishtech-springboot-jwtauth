package fi.ishtech.springboot.jwtauth.dto;

import java.io.Serial;

import fi.ishtech.base.vo.BaseStandardNoIdEntityVo;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
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

	private String firstName;

	private String middleName;

	private String lastName;

	private String defaultLang;

	public String getFullName() {
		if (middleName != null && !middleName.isBlank()) {
			return String.join(" ", firstName.strip(), middleName.strip(), lastName.strip());
		} else {
			return String.join(" ", firstName.strip(), lastName.strip());
		}
	}

}