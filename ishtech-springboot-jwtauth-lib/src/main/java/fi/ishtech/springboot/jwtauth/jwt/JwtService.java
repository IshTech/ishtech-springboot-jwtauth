package fi.ishtech.springboot.jwtauth.jwt;

import java.security.Key;
import java.util.Date;
import java.util.function.Function;

import javax.crypto.SecretKey;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.NumberUtils;

import fi.ishtech.springboot.jwtauth.userdetails.UserDetailsImpl;

import lombok.extern.slf4j.Slf4j;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

/**
 * Service for generating, validating, and extracting info from JWT tokens.
 *
 * @author Muneer Ahmed Syed
 */
@Component
@Slf4j
public class JwtService {

	private static final String STR_AUTHERIZATION = "Authorization";
	private static final String STR_BEARER = "Bearer ";

	@Value("${fi.ishtech.springboot.jwtauth.jwt.secret}")
	private String jwtSecret;

	@Value("${fi.ishtech.springboot.jwtauth.jwt.expirition-ms}")
	private Integer jwtExpirationMs;

	@Value("${fi.ishtech.springboot.jwtauth.jwt.issuer}")
	private String issuer;

	@Value("${fi.ishtech.springboot.jwtauth.login-by-email:true}")
	private boolean loginByEmail;

	/**
	 * Generates a JWT response from authentication.
	 *
	 * @param authentication {@link Authentication}
	 * @return {@link JwtResponse}
	 */
	public JwtResponse generateJwtResponse(Authentication authentication) {
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

		String jwt = generateJwtToken(userDetails);

		return JwtResponse.of(jwt);
	}

	/**
	 * Generates a JWT token for the user details.
	 *
	 * @param userDetails {@link UserDetails}
	 * @return JWT {@link String}
	 */
	public String generateJwtToken(UserDetails userDetails) {
		Date iat = new Date();
		Date exp = new Date(iat.getTime() + jwtExpirationMs);
		return generateJwtToken((UserDetailsImpl) userDetails, iat, exp);
	}

	/**
	 * Generates a JWT token with specified issued-at and expiration dates.
	 *
	 * @param userDetails {@link UserDetails}
	 * @param iat         issued-at {@link Date}
	 * @param exp         expiration {@link Date}
	 * @return JWT {@link String}
	 */
	private String generateJwtToken(UserDetailsImpl userDetails, Date iat, Date exp) {
		// @formatter:off
		return Jwts.builder()
				.subject((loginByEmail ? userDetails.getEmail() : userDetails.getUsername()))
				.issuedAt(iat)
				.expiration(exp)
				.issuer(issuer)
				.claim("userId", userDetails.getId())
				.claim("email", userDetails.getEmail())
				.claim("roles", userDetails.getRoleNames())
				.claim("fullName", userDetails.getFullName())
				.claim("lang", userDetails.getLang())
				.signWith(jwtKey())
				.compact();
		// @formatter:on
	}

	/**
	 * Validates the JWT token.
	 *
	 * @param token JWT {@link String}
	 * @return {@code true} if valid, {@code false} otherwise
	 */
	public boolean validateJwtToken(String token) {
		try {
			parseSignedClaims(token);
			return true;
		} catch (IllegalArgumentException | JwtException e) {
			log.error("Error in parsing JWT", e);
		} catch (Exception e) {
			log.error("Error in parsing JWT", e);
		}
		return false;
	}

	/**
	 * Extracts the username (subject) from the JWT token.
	 *
	 * @param token JWT {@link String}
	 * @return username {@link String}
	 */
	public String extractUsername(String token) {
		return extractClaim(token, Claims::getSubject);
	}

	/**
	 * Extracts the userId from JWT token.<br>
	 * Returns {@code null} if the claim is missing or blank.<br>
	 *
	 * @param token JWT {@link String}
	 * @return userId {@link Long}
	 * @throws NumberFormatException if the value is not a valid number.
	 */
	public Long extractUserId(String token) {
		Object userIdObj = extractClaims(token).get("userId");
		if (userIdObj == null) {
			return null;
		}

		if (userIdObj instanceof Number) {
			return ((Number) userIdObj).longValue();
		}

		if (userIdObj instanceof String) {
			return NumberUtils.parseNumber((String) userIdObj, Long.class);
		}

		throw new NumberFormatException("Invalid userId: " + userIdObj);

	}

	/**
	 * Extracts the user ID from the request's JWT.
	 *
	 * @param request the HTTP request
	 * @return the user ID or null
	 */
	public Long extractUserIdFromRequest(final HttpServletRequest request) {
		return extractUserId(extractJwtFromRequest(request));
	}

	/**
	 * Extracts the JWT token from the request.
	 *
	 * @param request the HTTP request
	 * @return the JWT token or null
	 */
	public String extractJwtFromRequest(final HttpServletRequest request) {
		return extractJwtFromRequestHeader(request.getHeader(STR_AUTHERIZATION));
	}

	/**
	 * Extracts the JWT from the authorization header.
	 *
	 * @param authHeader the authorization header
	 * @return JWT {@link String} or {@code null}
	 */
	private String extractJwtFromRequestHeader(final String authHeader) {
		if (authHeader != null && authHeader.startsWith(STR_BEARER)) {
			return authHeader.substring(7);
		}

		return null;
	}

	/**
	 * Extracts a claim from the JWT using the resolver function.
	 *
	 * @param <T>             the claim type
	 * @param token           JWT {@link String}
	 * @param claimsResolvers {@link Function}&lt;{@link Claims}, T&gt;
	 * @return the extracted claim
	 */
	private <T> T extractClaim(String token, Function<Claims, T> claimsResolvers) {
		return claimsResolvers.apply(extractClaims(token));
	}

	/**
	 * Extracts all claims from the JWT.
	 *
	 * @param token the JWT token
	 * @return the claims
	 */
	private Claims extractClaims(String token) {
		return parseSignedClaims(token).getPayload();
	}

	/**
	 * Parses and verifies the signed JWT claims.
	 *
	 * @param token JWT {@link String}
	 * @return the parsed claims {@link Jws}&lt;{@link Claims}&gt;
	 */
	private Jws<Claims> parseSignedClaims(String token) {
		try {
			// @formatter:off
			return Jwts
					.parser()
					.verifyWith((SecretKey) jwtKey())
					.build()
					.parseSignedClaims(token);
			// @formatter:on
		} catch (IllegalArgumentException | JwtException e) {
			log.error("Error in parsing JWT", e);
			throw e;
		} catch (Exception e) {
			log.error("Error in parsing JWT", e);
			throw new JwtException("Error in parsing JWT", e);
		}
	}

	/**
	 * Gets the JWT signing key.
	 *
	 * @return {@link Key}
	 */
	private Key jwtKey() {
		return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
	}

}