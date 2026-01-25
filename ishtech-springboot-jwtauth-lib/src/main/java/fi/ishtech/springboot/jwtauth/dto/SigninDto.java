package fi.ishtech.springboot.jwtauth.dto;

import java.io.Serial;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import fi.ishtech.base.vo.BaseVo;

import lombok.Data;

/**
 * DTO for sign-in
 *
 * @author Muneer Ahmed Syed
 */
@Data
public class SigninDto implements BaseVo {

	@Serial
	private static final long serialVersionUID = -7571955490506238597L;

	// TODO: either username or email is mandatory
	private String username;

	@NotBlank
	@Email
	private String email;

	@NotBlank
	private String password;

}