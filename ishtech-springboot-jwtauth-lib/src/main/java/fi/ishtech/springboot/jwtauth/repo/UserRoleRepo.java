package fi.ishtech.springboot.jwtauth.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.Repository;

import fi.ishtech.springboot.jwtauth.entity.UserRole;

/**
 * {@link Repository} for entity {@link UserRole}
 *
 * @author Muneer Ahmed Syed
 */
public interface UserRoleRepo extends JpaRepository<UserRole, Long> {

}