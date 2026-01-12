package fi.ishtech.springboot.jwtauth.payload.params;

import java.io.Serial;

import jakarta.validation.constraints.PositiveOrZero;

import fi.ishtech.base.payload.filter.BaseStandardNoIdEntityFilterParams;

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

	@PositiveOrZero
	private Long id;

	protected boolean idLikeSearch = false;

	private String firstName;

	private String middleName;

	private String lastName;

	private String nickName;

	private String title;

	private String prefix;

	private String suffix;

	private String defaultLang;

	private String email;

}