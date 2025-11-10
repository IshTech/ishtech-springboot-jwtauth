package fi.ishtech.springboot.jwtauth.dto;

import java.io.Serial;
import java.io.Serializable;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.ToString;

@Data
public class UpdatePasswordDto implements Serializable {

	@Serial
	private static final long serialVersionUID = 3504854805844405021L;

	@ToString.Exclude
	private String token;

	@ToString.Exclude
	@NotBlank
	private String password;

	@ToString.Exclude
	@NotBlank
	private String passwordConfirm;

	@AssertTrue(message = "password and passwordConfirm are not matching")
	@JsonIgnore
	public boolean isPasswordAndPasswordConfirmMatch() {
		return password.equals(passwordConfirm);
	}

}