package fi.ishtech.springboot.jwtauth.dto;

import java.io.Serial;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnore;

import fi.ishtech.base.vo.BaseVo;

import lombok.Data;
import lombok.ToString;

/**
 * DTO for sign-up
 *
 * @author Muneer Ahmed Syed
 */
@Data
public class SignupDto implements BaseVo {

	@Serial
	private static final long serialVersionUID = -7883197620560603955L;

	@NotBlank
	private String firstName;

	@NotBlank
	private String lastName;

	private String username;

	@NotBlank
	@Email
	private String email;

	@ToString.Exclude
	@NotBlank
	private String password;

	@ToString.Exclude
	@NotBlank
	private String passwordConfirm;

	/**
	 * Checks that {@link #password} and {@link #passwordConfirm} are same.
	 *
	 * @return {@code true} if matching or else {@code false}
	 */
	@AssertTrue(message = "password and passwordConfirm are not matching")
	@JsonIgnore
	public boolean validatePasswordAndPasswordConfirmMatch() {
		return password.equals(passwordConfirm);
	}

	/**
	 * I18N language code, e.g. {@code en}, {@code fi} etc.
	 */
	private String lang;

	/**
	 * Validates input is I18N language code when present.
	 *
	 * @return {@code true} if valid or else {@code false}
	 */
	@AssertTrue(message = "lang must be exactly 2 lowercase letters")
	@JsonIgnore
	public boolean validateLang() {
		return lang != null && !lang.isBlank() ? lang.matches("^[a-z]{2}$") : true;
	}

	@AssertTrue
	private boolean acceptTermsConditions;

}