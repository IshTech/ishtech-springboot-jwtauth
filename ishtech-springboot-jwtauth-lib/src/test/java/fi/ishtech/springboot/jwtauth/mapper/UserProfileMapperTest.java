package fi.ishtech.springboot.jwtauth.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import fi.ishtech.springboot.jwtauth.dto.SignupDto;
import fi.ishtech.springboot.jwtauth.dto.UserProfileDto;
import fi.ishtech.springboot.jwtauth.entity.UserProfile;

/**
 * Test class for {@link UserProfileMapper}
 *
 * @author Muneer Ahmed Syed
 */
public class UserProfileMapperTest {

	private final UserProfileMapper userProfileMapper = new UserProfileMapperImpl();

	/**
	 * Builds a fully populated existing entity, as loaded from the DB before an update.
	 *
	 * @return {@link UserProfile}
	 */
	private UserProfile existingEntity() {
		UserProfile entity = new UserProfile();
		entity.setId(1L);
		entity.setFirstName("Api");
		entity.setMiddleName("Keski");
		entity.setLastName("Test");
		entity.setNickName("Apsu");
		entity.setTitle("Dr");
		entity.setPrefix("Mr");
		entity.setSuffix("Jr");
		entity.setDefaultLang("fi");
		entity.setDescription("original description");
		entity.setActive(true);

		return entity;
	}

	/**
	 * Builds a dto carrying only the properties that a rename supplies.
	 *
	 * @return {@link UserProfileDto}
	 */
	private UserProfileDto partialDto() {
		UserProfileDto dto = new UserProfileDto();
		dto.setId(1L);
		dto.setFirstName("New Api");
		dto.setLastName("New Test");

		return dto;
	}

	// toExistingEntity: HTTP PUT, a full replacement

	@Test
	void testToExistingEntityClearsPropertiesAbsentFromDto() {
		UserProfile entity = userProfileMapper.toExistingEntity(partialDto(), existingEntity());

		assertEquals("New Api", entity.getFirstName());
		assertEquals("New Test", entity.getLastName());

		assertNull(entity.getMiddleName(), "PUT replaces the resource, so an absent property is cleared");
		assertNull(entity.getNickName());
		assertNull(entity.getTitle());
		assertNull(entity.getPrefix());
		assertNull(entity.getSuffix());
		assertNull(entity.getDefaultLang());
		assertNull(entity.getDescription());
	}

	@Test
	void testToExistingEntityKeepsIdWhenAbsentFromDto() {
		UserProfileDto dto = partialDto();
		dto.setId(null);

		UserProfile entity = userProfileMapper.toExistingEntity(dto, existingEntity());

		assertEquals(1L, entity.getId(), "id is protected, so a body without an id cannot null the identifier");
	}

	@Test
	void testToExistingEntityKeepsActiveWhenAbsentFromDto() {
		UserProfile entity = userProfileMapper.toExistingEntity(partialDto(), existingEntity());

		assertTrue(entity.isActive(), "isActive is primitive in the entity, so MapStruct skips a null");
	}

	@Test
	void testToExistingEntityAssignsEveryPropertyPresentInDto() {
		UserProfileDto dto = new UserProfileDto();
		dto.setId(1L);
		dto.setFirstName("New Api");
		dto.setMiddleName("New Keski");
		dto.setLastName("New Test");
		dto.setNickName("New Apsu");
		dto.setTitle("Prof");
		dto.setPrefix("Ms");
		dto.setSuffix("Sr");
		dto.setDefaultLang("sv");
		dto.setDescription("new description");
		dto.setActive(false);

		UserProfile entity = userProfileMapper.toExistingEntity(dto, existingEntity());

		assertEquals(1L, entity.getId());
		assertEquals("New Api", entity.getFirstName());
		assertEquals("New Keski", entity.getMiddleName());
		assertEquals("New Test", entity.getLastName());
		assertEquals("New Apsu", entity.getNickName());
		assertEquals("Prof", entity.getTitle());
		assertEquals("Ms", entity.getPrefix());
		assertEquals("Sr", entity.getSuffix());
		assertEquals("sv", entity.getDefaultLang());
		assertEquals("new description", entity.getDescription());
		assertEquals(false, entity.isActive());
	}

	@Test
	void testToExistingEntityReturnsSameEntityForNullDto() {
		UserProfile entity = existingEntity();

		UserProfile result = userProfileMapper.toExistingEntity(null, entity);

		assertSame(entity, result);
		assertEquals("fi", result.getDefaultLang());
	}

	// patchExistingEntity: HTTP PATCH, a partial update

	@Test
	void testPatchExistingEntityKeepsDefaultLangWhenAbsentFromDto() {
		UserProfile entity = userProfileMapper.patchExistingEntity(partialDto(), existingEntity());

		assertEquals("fi", entity.getDefaultLang(), "defaultLang must survive a partial update");
	}

	@Test
	void testPatchExistingEntityKeepsEveryPropertyAbsentFromDto() {
		UserProfile entity = userProfileMapper.patchExistingEntity(partialDto(), existingEntity());

		assertEquals(1L, entity.getId());
		assertEquals("New Api", entity.getFirstName());
		assertEquals("New Test", entity.getLastName());
		assertEquals("Keski", entity.getMiddleName());
		assertEquals("Apsu", entity.getNickName());
		assertEquals("Dr", entity.getTitle());
		assertEquals("Mr", entity.getPrefix());
		assertEquals("Jr", entity.getSuffix());
		assertEquals("original description", entity.getDescription());
		assertTrue(entity.isActive());
	}

	@Test
	void testPatchExistingEntityOverwritesPropertiesPresentInDto() {
		UserProfileDto dto = new UserProfileDto();
		dto.setId(1L);
		dto.setFirstName("New Api");
		dto.setMiddleName("New Keski");
		dto.setLastName("New Test");
		dto.setNickName("New Apsu");
		dto.setTitle("Prof");
		dto.setPrefix("Ms");
		dto.setSuffix("Sr");
		dto.setDefaultLang("sv");
		dto.setDescription("new description");

		UserProfile entity = userProfileMapper.patchExistingEntity(dto, existingEntity());

		assertEquals("New Api", entity.getFirstName());
		assertEquals("New Keski", entity.getMiddleName());
		assertEquals("New Test", entity.getLastName());
		assertEquals("New Apsu", entity.getNickName());
		assertEquals("Prof", entity.getTitle());
		assertEquals("Ms", entity.getPrefix());
		assertEquals("Sr", entity.getSuffix());
		assertEquals("sv", entity.getDefaultLang());
		assertEquals("new description", entity.getDescription());
	}

	@Test
	void testPatchExistingEntityKeepsIdWhenAbsentFromDto() {
		UserProfileDto dto = partialDto();
		dto.setId(null);

		UserProfile entity = userProfileMapper.patchExistingEntity(dto, existingEntity());

		assertEquals(1L, entity.getId());
	}

	@Test
	void testPatchExistingEntityReturnsSameEntityForNullDto() {
		UserProfile entity = existingEntity();

		UserProfile result = userProfileMapper.patchExistingEntity(null, entity);

		assertSame(entity, result);
		assertEquals("fi", result.getDefaultLang());
	}

	// toNewEntity and toBriefDto

	@Test
	void testToNewEntityDefaultsDefaultLangWhenSignupHasNoLang() {
		SignupDto signupDto = new SignupDto();
		signupDto.setFirstName("Api");
		signupDto.setLastName("Test");

		UserProfile entity = userProfileMapper.toNewEntity(signupDto);

		assertEquals("en", entity.getDefaultLang());
		assertTrue(entity.isActive());
	}

	@Test
	void testToNewEntityUsesSignupLangAsDefaultLang() {
		SignupDto signupDto = new SignupDto();
		signupDto.setFirstName("Api");
		signupDto.setLastName("Test");
		signupDto.setLang("fi");

		UserProfile entity = userProfileMapper.toNewEntity(signupDto);

		assertEquals("fi", entity.getDefaultLang());
	}

	@Test
	void testToBriefDtoMapsDefaultLang() {
		UserProfileDto dto = userProfileMapper.toBriefDto(existingEntity());

		assertEquals("fi", dto.getDefaultLang());
		assertEquals(1L, dto.getId());
	}

}
