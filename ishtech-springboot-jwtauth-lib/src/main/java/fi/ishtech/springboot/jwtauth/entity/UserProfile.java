package fi.ishtech.springboot.jwtauth.entity;

import java.io.Serial;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import fi.ishtech.base.entity.BaseStandardNoIdEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @author Muneer Ahmed Syed
 */
@Entity
@Table(name = "t_user_profile")
@DynamicInsert
@DynamicUpdate
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class UserProfile extends BaseStandardNoIdEntity {

	@Serial
	private static final long serialVersionUID = -6549501435122538676L;

	@Id
	@Column(name = "id", nullable = false, insertable = true, updatable = false)
	private Long id;

	@Column(name = "first_name", nullable = false, insertable = true, updatable = true)
	private String firstName;

	@Column(name = "middle_name", nullable = true, insertable = true, updatable = true)
	private String middleName;

	@Column(name = "last_name", nullable = false, insertable = true, updatable = true)
	private String lastName;

	@Column(name = "nick_name", nullable = true, insertable = true, updatable = true)
	private String nickName;

	@Column(name = "title", nullable = true, insertable = true, updatable = true)
	private String title;

	@Column(name = "prefix", nullable = true, insertable = true, updatable = true)
	private String prefix;

	@Column(name = "suffix", nullable = true, insertable = true, updatable = true)
	private String suffix;

	@Column(name = "default_lang", length = 2, nullable = false, insertable = true, updatable = true)
	private String defaultLang;

	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@OneToOne(optional = false, fetch = FetchType.EAGER)
	@JoinColumn(name = "id", referencedColumnName = "id", nullable = true, insertable = false, updatable = false,
			foreignKey = @ForeignKey(name = "fk_user_profile_user"))
	private User user;

	public String getFullName() {
		if (middleName != null && !middleName.isBlank()) {
			return String.join(" ", firstName == null ? "" : firstName.strip(), middleName.strip(),
					lastName == null ? "" : lastName.strip());
		} else {
			return String.join(" ", firstName == null ? "" : firstName.strip(),
					lastName == null ? "" : lastName.strip());
		}
	}

	public String getEmail() {
		return getUser() == null ? null : user.getEmail();
	}

}