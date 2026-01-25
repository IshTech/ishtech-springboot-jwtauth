package fi.ishtech.springboot.jwtauth.jwt;

import java.io.Serial;

import com.fasterxml.jackson.annotation.JsonProperty;

import fi.ishtech.base.vo.BaseVo;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

/**
 * Custom response for JWT
 *
 * @author Muneer Ahmed Syed
 */
@Builder(access = AccessLevel.PRIVATE)
@Getter
public class JwtResponse implements BaseVo {

	@Serial
	private static final long serialVersionUID = -6607232579525545805L;

	@JsonProperty("token_type")
	@Builder.Default
	private String tokenType = "Bearer";

	@JsonProperty("access_token")
	private String accessToken;

	/**
	 * Creates {@link JwtResponse} with access token
	 *
	 * @param accessToken {@link String}
	 * @return {@link JwtResponse}
	 */
	public static JwtResponse of(String accessToken) {
		// @formatter:off
		return JwtResponse.builder()
				.accessToken(accessToken)
				.build();
		// @formatter:on
	}

}