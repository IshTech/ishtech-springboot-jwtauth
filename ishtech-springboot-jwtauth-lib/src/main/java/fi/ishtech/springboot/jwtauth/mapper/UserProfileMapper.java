package fi.ishtech.springboot.jwtauth.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.InheritConfiguration;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import fi.ishtech.springboot.base.annotations.mapstruct.BriefMapping;
import fi.ishtech.springboot.base.mapper.BaseStandardNoIdMapper;
import fi.ishtech.springboot.jwtauth.dto.SignupDto;
import fi.ishtech.springboot.jwtauth.dto.UserProfileDto;
import fi.ishtech.springboot.jwtauth.entity.UserProfile;

/**
 * Mapper for UserProfile from input DTOs
 *
 * @author Muneer Ahmed Syed
 */
@Mapper(componentModel = "spring")
public interface UserProfileMapper extends BaseStandardNoIdMapper {

	/**
	 * Mapping from entity to dto of direct fields
	 *
	 * @param entity {@link UserProfile}
	 * @return {@link UserProfileDto}
	 */
	@BriefMapping
	@BeanMapping(ignoreByDefault = true)
	@InheritConfiguration(name = "toBaseStandardNoIdVo")
	@Mapping(source = "id", target = "id")
	@Mapping(source = "firstName", target = "firstName")
	@Mapping(source = "middleName", target = "middleName")
	@Mapping(source = "lastName", target = "lastName")
	@Mapping(source = "nickName", target = "nickName")
	@Mapping(source = "title", target = "title")
	@Mapping(source = "prefix", target = "prefix")
	@Mapping(source = "suffix", target = "suffix")
	@Mapping(source = "defaultLang", target = "defaultLang")
	@Mapping(source = "email", target = "email")
	UserProfileDto toBriefDto(UserProfile entity);

	/**
	 * Mapping from input dto to new entity
	 *
	 * @param signupDto {@link SignupDto}
	 * @return new {@link UserProfile} entity
	 */
	@BeanMapping(ignoreByDefault = true)
	@Mapping(target = "id", ignore = true)
	@Mapping(source = "firstName", target = "firstName")
	@Mapping(source = "lastName", target = "lastName")
	@Mapping(source = "lang", target = "defaultLang", defaultValue = "en")
	@Mapping(target = "active", constant = "true")
	UserProfile toNewEntity(SignupDto signupDto);

	/**
	 * Mapping from dto to existing entity, replacing every mapped property (HTTP {@code PUT}).
	 * <p>
	 * {@code PUT} is a full replacement: the request body carries the complete resource, so a {@code null} in the dto is
	 * a deliberate instruction to clear that property. Every mapped property is therefore assigned as given, and only
	 * {@code id} is protected, so that a body without an {@code id} cannot null the identifier of the loaded entity.
	 * <p>
	 * Properties the resource requires are enforced before this method is reached, by the
	 * {@link UserProfileDto.OnReplace} validation group, so a {@code PUT} that omits one is rejected with
	 * {@code 400 Bad Request} rather than clearing a column the entity declares as non-null.
	 * <p>
	 * {@code isActive} is nullable in the VO and primitive in the entity. MapStruct automatically skips the assignment
	 * when the VO value is {@code null}, preserving the entity's existing value.
	 *
	 * @param dto    {@link UserProfileDto}
	 * @param entity {@link UserProfile}
	 * @return updated {@link UserProfile} entity
	 *
	 * @see #patchExistingEntity(UserProfileDto, UserProfile)
	 */
	@InheritInverseConfiguration(name = "toBriefDto")
	@Mapping(source = "id", target = "id", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
	UserProfile toExistingEntity(UserProfileDto dto, @MappingTarget UserProfile entity);

	/**
	 * Mapping from dto to existing entity, for partial updates (HTTP {@code PATCH}).
	 * <p>
	 * {@code PATCH} is a partial update: a property absent from the request body arrives as {@code null} in the dto and
	 * must leave the loaded entity untouched. {@link NullValuePropertyMappingStrategy#IGNORE} is therefore set at bean
	 * level, so it applies to every mapped property rather than to a single one.
	 * <p>
	 * A consequence of carrying absence as {@code null} is that this method cannot clear a property. Use
	 * {@link #toExistingEntity(UserProfileDto, UserProfile)} to clear one.
	 * <p>
	 * {@code isActive} is nullable in the VO and primitive in the entity. MapStruct automatically skips the assignment
	 * when the VO value is {@code null}, preserving the entity's existing value.
	 *
	 * @param dto    {@link UserProfileDto}
	 * @param entity {@link UserProfile}
	 * @return updated {@link UserProfile} entity
	 *
	 * @see #toExistingEntity(UserProfileDto, UserProfile)
	 */
	@InheritInverseConfiguration(name = "toBriefDto")
	@BeanMapping(ignoreByDefault = true,
			nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
	UserProfile patchExistingEntity(UserProfileDto dto, @MappingTarget UserProfile entity);

}