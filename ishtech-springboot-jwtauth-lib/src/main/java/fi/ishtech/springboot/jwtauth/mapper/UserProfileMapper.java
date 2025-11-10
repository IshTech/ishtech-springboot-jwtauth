package fi.ishtech.springboot.jwtauth.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import fi.ishtech.base.mapper.BaseStandardNoIdMapper;
import fi.ishtech.springboot.jwtauth.dto.SignupDto;
import fi.ishtech.springboot.jwtauth.dto.UserProfileDto;
import fi.ishtech.springboot.jwtauth.entity.UserProfile;

/**
 *
 * @author Muneer Ahmed Syed
 */
@Mapper(componentModel = "spring")
public interface UserProfileMapper extends BaseStandardNoIdMapper {

	/**
	 *
	 * @param entity {@link UserProfile}
	 * @return {@link UserProfileDto}
	 */
	@BeanMapping(ignoreByDefault = true)
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
	 *
	 * @param signupDto {@link SignupDto}
	 * @return new {@link UserProfile} entity
	 */
	@BeanMapping(ignoreByDefault = true)
	@Mapping(target = "id", ignore = true)
	@Mapping(source = "firstName", target = "firstName")
	@Mapping(source = "lastName", target = "lastName")
	@Mapping(source = "lang", target = "defaultLang", defaultValue = "en")
	UserProfile toNewEntity(SignupDto signupDto);

	/**
	 *
	 * @param dto    {@link UserProfileDto}
	 * @param entity {@link UserProfile}
	 * @return updated {@link UserProfile} entity
	 */
	@InheritInverseConfiguration(name = "toBriefDto")
	@Mapping(source = "id", target = "id", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
	UserProfile toEntity(UserProfileDto dto, @MappingTarget UserProfile entity);

}