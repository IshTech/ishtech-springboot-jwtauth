package fi.ishtech.springboot.jwtauth.entity;

import java.io.Serial;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.fasterxml.jackson.annotation.JsonIgnore;

import fi.ishtech.base.entity.BaseStandardEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
//import org.hibernate.envers.NotAudited;

/**
 * {@link Entity} for DB table for user
 *
 * @author Muneer Ahmed Syed
 */
@Entity
@Table(name = "t_user")
@DynamicInsert
@DynamicUpdate
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class User extends BaseStandardEntity {

	@Serial
	private static final long serialVersionUID = -6637213531718295584L;

	@Column(nullable = false, updatable = false, unique = true)
	private String username;

	@Column(nullable = false, updatable = false, unique = true)
	private String email;

	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@JsonIgnore
	// @NotAudited
	@Column(nullable = false, updatable = true)
	private String passwordHash;

	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@JsonIgnore
	// @NotAudited
	@Column(name = "password_reset_token", nullable = true, insertable = false, updatable = true)
	private String passwordResetToken;

	@Column(name = "force_change_password", nullable = true, insertable = true, updatable = true)
	private boolean forceChangePassword;

	@Column(name = "is_email_verified", nullable = true, insertable = true, updatable = false)
	private boolean isEmailVerified;

	@Column(name = "is_active", nullable = false, insertable = true, updatable = true)
	private boolean isActive;

	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	private Set<UserRole> userRoles;

	@OneToOne(mappedBy = "user", fetch = FetchType.LAZY)
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	private UserProfile userProfile;

}