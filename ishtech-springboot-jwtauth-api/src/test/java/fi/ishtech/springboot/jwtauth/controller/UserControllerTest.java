package fi.ishtech.springboot.jwtauth.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import fi.ishtech.springboot.jwtauth.dto.UserProfileDto;
import fi.ishtech.springboot.jwtauth.enums.StandardUserRoleEnum;
import fi.ishtech.springboot.jwtauth.exception.UserNotFoundException;
import fi.ishtech.springboot.jwtauth.service.AuthInfoService;
import fi.ishtech.springboot.jwtauth.service.UserProfileService;
import fi.ishtech.springboot.jwtauth.userdetails.UserDetailsImpl;

/**
 * Test class for {@link UserController}
 *
 * @author Muneer Ahmed Syed
 */
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@AutoConfigureMockMvc
public class UserControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockitoBean
	private UserProfileService userProfileService;

	@MockitoBean
	private AuthInfoService authInfoService;

	private UserProfileDto sampleUserProfile;

	private UserDetails sampleUserDetails;

	@BeforeEach
	void setUp() {
		sampleUserProfile = new UserProfileDto();
		sampleUserProfile.setId(100L);
		sampleUserProfile.setFirstName("Testi");
		sampleUserProfile.setLastName("Besti");

		sampleUserDetails = UserDetailsImpl.of(100L, "testi", "test@test.com", "pass", true,
				List.of(StandardUserRoleEnum.asRole(StandardUserRoleEnum.USER)), "Testi Besti", "en");
	}

	@Test
	@Order(1)
	void testFindUserProfileByIdByAdmin() throws Exception {
		when(userProfileService.findOneByIdAndMapToVoOrElseThrow(100L)).thenReturn(sampleUserProfile);

		// @formatter:off
		mockMvc.perform(get("/api/v1/users/100")
				.with(user("admin").authorities(() -> "ROLE_ADMIN")))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.firstName").value("Testi"));
		// @formatter:on
	}

	@Test
	@Order(2)
	void testFindUserProfileByUserIdBySelf() throws Exception {
		when(authInfoService.getUserId()).thenReturn(100L);
		when(userProfileService.findOneByIdAndMapToVoOrElseThrow(100L)).thenReturn(sampleUserProfile);

		// @formatter:off
		mockMvc.perform(get("/api/v1/users/100")
				.with(user(sampleUserDetails)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.firstName").value("Testi"));
		// @formatter:on
	}

	@Test
	@Order(3)
	void testFindUserProfileByMeBySelf() throws Exception {
		when(authInfoService.getUserId()).thenReturn(100L);
		when(userProfileService.findOneByIdAndMapToVoOrElseThrow(100L)).thenReturn(sampleUserProfile);

		// @formatter:off
		mockMvc.perform(get("/api/v1/users/me")
				.with(user(sampleUserDetails)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.firstName").value("Testi"));
		// @formatter:on
	}

	@Test
	@Order(4)
	void testFindUserProfileByNonExistentIdByAdmin() throws Exception {
		when(userProfileService.findOneByIdAndMapToVoOrElseThrow(9999L)).thenThrow(new UserNotFoundException(9999L));

		// @formatter:off
		mockMvc.perform(get("/api/v1/users/9999")
				.with(user("admin").authorities(() -> "ROLE_ADMIN")))
				.andExpect(status().isNotFound());
		// @formatter:on
	}

	@Test
	@Order(5)
	void testUpdateUserProfileByNonExistentIdByAdmin() throws Exception {
		when(authInfoService.getUserId()).thenReturn(1L);
		when(authInfoService.isAdmin()).thenReturn(true);
		when(userProfileService.updateAndMapToDto(any(UserProfileDto.class)))
				.thenThrow(new UserNotFoundException(9999L));

		// @formatter:off
		mockMvc.perform(put("/api/v1/users/9999")
				.with(user("admin").authorities(() -> "ROLE_ADMIN"))
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"id\":9999,\"firstName\":\"Testi\",\"lastName\":\"Besti\",\"defaultLang\":\"en\"}"))
				.andExpect(status().isNotFound());
		// @formatter:on
	}

	@Test
	@Order(6)
	void testUpdateUserProfileRejectsBodyWithoutDefaultLang() throws Exception {
		when(authInfoService.getUserId()).thenReturn(100L);

		// @formatter:off
		mockMvc.perform(put("/api/v1/users/100")
				.with(user(sampleUserDetails))
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"id\":100,\"firstName\":\"Testi\",\"lastName\":\"Besti\"}"))
				.andExpect(status().isBadRequest());
		// @formatter:on

		verify(userProfileService, never()).updateAndMapToDto(any(UserProfileDto.class));
	}

	@Test
	@Order(7)
	void testUpdateUserProfileAcceptsCompleteBody() throws Exception {
		when(authInfoService.getUserId()).thenReturn(100L);
		when(userProfileService.updateAndMapToDto(any(UserProfileDto.class))).thenReturn(sampleUserProfile);

		// @formatter:off
		mockMvc.perform(put("/api/v1/users/100")
				.with(user(sampleUserDetails))
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"id\":100,\"firstName\":\"Testi\",\"lastName\":\"Besti\",\"defaultLang\":\"en\"}"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.firstName").value("Testi"));
		// @formatter:on

		verify(userProfileService).updateAndMapToDto(any(UserProfileDto.class));
	}

	@Test
	@Order(8)
	void testPatchUserProfileAcceptsPartialBodyWithoutDefaultLang() throws Exception {
		when(authInfoService.getUserId()).thenReturn(100L);
		when(userProfileService.patchAndMapToDto(any(UserProfileDto.class))).thenReturn(sampleUserProfile);

		// @formatter:off
		mockMvc.perform(patch("/api/v1/users/100")
				.with(user(sampleUserDetails))
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"id\":100,\"firstName\":\"Testi\",\"lastName\":\"Besti\"}"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.firstName").value("Testi"));
		// @formatter:on

		verify(userProfileService).patchAndMapToDto(any(UserProfileDto.class));
		verify(userProfileService, never()).updateAndMapToDto(any(UserProfileDto.class));
	}

	@Test
	@Order(9)
	void testPatchUserProfileRejectsInvalidDefaultLang() throws Exception {
		when(authInfoService.getUserId()).thenReturn(100L);

		// @formatter:off
		mockMvc.perform(patch("/api/v1/users/100")
				.with(user(sampleUserDetails))
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"id\":100,\"defaultLang\":\"eng\"}"))
				.andExpect(status().isBadRequest());
		// @formatter:on

		verify(userProfileService, never()).patchAndMapToDto(any(UserProfileDto.class));
	}

}