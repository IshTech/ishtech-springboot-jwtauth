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
	 * Mapping from DTO to existing entity, for full updates
	 * <p>
	 * {@code isActive} is nullable in the DTO and primitive in the entity. MapStruct automatically skips the
	 * assignment, so {@code isActive} missing or {@code null} in the DTO is ignored, preserving the existing value in
	 * the entity
	 * <p>
	 * An attribute missing or {@code null} (except {@code id}) in the DTO is overwritten in the entity as {@code null}
	 * <p>
	 * Used by the {@code PUT} API
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
	 * Mapping from DTO to existing entity, for partial updates
	 * <p>
	 * {@code isActive} is nullable in the DTO and primitive in the entity. MapStruct automatically skips the
	 * assignment, so {@code isActive} missing or {@code null} in the DTO is ignored, preserving the existing value in
	 * the entity
	 * <p>
	 * An attribute missing or {@code null} in the DTO is ignored, preserving the existing value in the entity
	 * <p>
	 * Used by the {@code PATCH} API
	 *
	 * @param dto    {@link UserProfileDto}
	 * @param entity {@link UserProfile}
	 * @return updated {@link UserProfile} entity
	 *
	 * @see #toExistingEntity(UserProfileDto, UserProfile)
	 */
	@InheritInverseConfiguration(name = "toBriefDto")
	@BeanMapping(ignoreByDefault = true, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
	UserProfile patchExistingEntity(UserProfileDto dto, @MappingTarget UserProfile entity);

}