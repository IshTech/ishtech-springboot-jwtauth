package fi.ishtech.springboot.jwtauth.repo;

import org.springframework.data.repository.Repository;

import fi.ishtech.base.repo.BaseStandardNoIdRepo;
import fi.ishtech.springboot.jwtauth.entity.UserProfile;

/**
 * {@link Repository} for entity {@link UserProfile}
 *
 * @author Muneer Ahmed Syed
 */
public interface UserProfileRepo extends BaseStandardNoIdRepo<UserProfile, Long> {

}