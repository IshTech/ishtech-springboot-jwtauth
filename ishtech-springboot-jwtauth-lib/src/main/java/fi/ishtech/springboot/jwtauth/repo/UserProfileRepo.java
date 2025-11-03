package fi.ishtech.springboot.jwtauth.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.Repository;

import fi.ishtech.springboot.jwtauth.entity.UserProfile;

/**
 * {@link Repository} for entity {@link UserProfile}
 *
 * @author Muneer Ahmed Syed
 */
public interface UserProfileRepo extends JpaRepository<UserProfile, Long> {

}