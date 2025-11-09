package fi.ishtech.springboot.jwtauth.payload.params;

import java.io.Serial;

import fi.ishtech.base.payload.filter.BaseStandardNoIdEntityFilterParams;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 *
 * @author Muneer Ahmed Syed
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class UserProfileFilterParams extends BaseStandardNoIdEntityFilterParams {

	@Serial
	private static final long serialVersionUID = -6062451974582082883L;

	@Positive
	private Long id;

	private String firstName;

	private String middleName;

	private String lastName;

	private String title;

	private String prefix;

	private String suffix;

	private String mobilePhone;

	private String defaultLang;

	private Long addressId;

	private String email;

}