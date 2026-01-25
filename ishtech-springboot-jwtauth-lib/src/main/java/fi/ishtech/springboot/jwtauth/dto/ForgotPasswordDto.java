package fi.ishtech.springboot.jwtauth.dto;

import java.io.Serial;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import fi.ishtech.base.vo.BaseVo;

import lombok.Data;

/**
 * Dto for forgot password input
 *
 * @author Muneer Ahmed Syed
 */
@Data
public class ForgotPasswordDto implements BaseVo {

	@Serial
	private static final long serialVersionUID = -7311190433437016528L;

	@NotBlank
	@Email
	private String email;

}