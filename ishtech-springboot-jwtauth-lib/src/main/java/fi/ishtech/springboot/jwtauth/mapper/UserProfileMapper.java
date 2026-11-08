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
	 * A {@code null} in the dto clears that property, except for {@code id}, which is configured to be skipped, and
	 * {@code isActive}, which MapStruct skips because it is {@link Boolean} in the dto and primitive in the entity.
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
	 * A {@code null} in the dto leaves that property as stored, so this method cannot clear one.
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