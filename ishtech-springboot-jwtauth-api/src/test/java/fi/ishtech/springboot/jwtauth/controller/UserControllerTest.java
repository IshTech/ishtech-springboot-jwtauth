package fi.ishtech.springboot.jwtauth.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import fi.ishtech.springboot.jwtauth.dto.UserProfileDto;
import fi.ishtech.springboot.jwtauth.service.AuthInfoService;
import fi.ishtech.springboot.jwtauth.service.UserProfileService;

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

	private UserProfileDto sampleUser;

	@BeforeEach
	void setUp() {
		sampleUser = new UserProfileDto();
		sampleUser.setId(100L);
		sampleUser.setFirstName("Testi");
		sampleUser.setLastName("Besti");
	}

	@Test
	@Order(1)
	@WithMockUser(username = "admin", authorities = "ROLE_ADMIN")
	void testFindUserProfileByIdByAdmin() throws Exception {
		when(authInfoService.getUserId()).thenReturn(99L);
		when(userProfileService.findOneByIdAndMapToVoOrElseThrow(100L)).thenReturn(sampleUser);

		// @formatter:off
		mockMvc.perform(get("/api/v1/users/100"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.firstName").value("Testi"));
		// @formatter:on
	}

	@Test
	@Order(2)
	@WithMockUser(username = "testi", authorities = "ROLE_USER")
	void testFindUserProfileByUserIdBySelf() throws Exception {
		when(authInfoService.getUserId()).thenReturn(100L);
		when(userProfileService.findOneByIdAndMapToVoOrElseThrow(100L)).thenReturn(sampleUser);

		// @formatter:off
		mockMvc.perform(get("/api/v1/users/100"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.firstName").value("Testi"));
		// @formatter:on
	}

	@Test
	@Order(3)
	@WithMockUser(username = "testi", authorities = "ROLE_USER")
	void testFindUserProfileByMeBySelf() throws Exception {
		when(authInfoService.getUserId()).thenReturn(100L);
		when(userProfileService.findOneByIdAndMapToVoOrElseThrow(100L)).thenReturn(sampleUser);

		// @formatter:off
		mockMvc.perform(get("/api/v1/users/me"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.firstName").value("Testi"));
		// @formatter:on
	}

}