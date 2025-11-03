package fi.ishtech.springboot.jwtauth.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import fi.ishtech.springboot.jwtauth.dto.SignupDto;
import fi.ishtech.springboot.jwtauth.dto.UserProfileDto;
import fi.ishtech.springboot.jwtauth.entity.UserProfile;
import fi.ishtech.springboot.jwtauth.mapper.UserProfileMapper;
import fi.ishtech.springboot.jwtauth.repo.UserProfileRepo;
import fi.ishtech.springboot.jwtauth.service.UserProfileService;
import jakarta.transaction.Transactional;
import jakarta.transaction.Transactional.TxType;
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

	@Transactional(TxType.MANDATORY)
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

}