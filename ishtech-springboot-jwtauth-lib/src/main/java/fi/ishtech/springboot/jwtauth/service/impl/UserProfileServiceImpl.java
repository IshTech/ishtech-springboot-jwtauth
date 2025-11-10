package fi.ishtech.springboot.jwtauth.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;

import fi.ishtech.springboot.jwtauth.dto.SignupDto;
import fi.ishtech.springboot.jwtauth.dto.UserProfileDto;
import fi.ishtech.springboot.jwtauth.entity.UserProfile;
import fi.ishtech.springboot.jwtauth.mapper.UserProfileMapper;
import fi.ishtech.springboot.jwtauth.repo.UserProfileRepo;
import fi.ishtech.springboot.jwtauth.service.UserProfileService;
import fi.ishtech.springboot.jwtauth.spec.UserProfileSpec;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 *
 * @author Muneer Ahmed Syed
 */
@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
@Validated
public class UserProfileServiceImpl implements UserProfileService {

	private final UserProfileRepo userProfileRepo;
	private final UserProfileMapper userProfileMapper;

	@Override
	public UserProfileRepo getRepo() {
		return userProfileRepo;
	}

	@Override
	public UserProfileMapper getMapper() {
		return userProfileMapper;
	}

	@Override
	@Transactional(readOnly = true)
	public UserProfileDto findOneByIdAndMapToVoOrElseThrow(Long id) {
		return userProfileMapper.toBriefDto(findOneByIdOrElseThrow(id));
	}

	@Transactional(readOnly = true)
	@Override
	public Page<UserProfileDto> findAllAndMapToVo(UserProfileSpec spec, Pageable pageable) {
		return this.findAll(spec, pageable).map(getMapper()::toBriefVo);
	}

	@Transactional(propagation = Propagation.MANDATORY)
	@Override
	public UserProfileDto create(Long userId, SignupDto signupDto) {
		log.debug("Creating new UserProfile for User({})", userId);

		UserProfile userProfile = userProfileMapper.toNewEntity(signupDto);
		userProfile.setId(userId);

		userProfile = userProfileRepo.save(userProfile);
		log.info("Created new UserProfile({})", userProfile.getId());

		UserProfileDto userProfileDto = userProfileMapper.toBriefDto(userProfile);

		return userProfileDto;
	}

	@Override
	public UserProfileDto updateAndMapToDto(UserProfileDto userProfileDto) {
		Assert.notNull(userProfileDto.getId(), "Input id is mandatory in input UserProfileDto");

		UserProfile userProfile = findOneByIdOrElseThrow(userProfileDto.getId());

		userProfileMapper.toEntity(userProfileDto, userProfile);

		userProfile = userProfileRepo.save(userProfile);

		return userProfileMapper.toBriefDto(userProfile);
	}

}