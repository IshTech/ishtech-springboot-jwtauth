package fi.ishtech.springboot.jwtauth.spec;

import java.io.Serial;
import java.util.List;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import org.springframework.util.StringUtils;

import fi.ishtech.base.spec.BaseStandardNoIdSpec;
import fi.ishtech.springboot.jwtauth.entity.User;
import fi.ishtech.springboot.jwtauth.entity.UserProfile;
import fi.ishtech.springboot.jwtauth.entity.UserProfile_;
import fi.ishtech.springboot.jwtauth.entity.User_;
import fi.ishtech.springboot.jwtauth.payload.params.UserProfileFilterParams;

/**
 *
 * @author Muneer Ahmed Syed
 */
public class UserProfileSpec extends BaseStandardNoIdSpec<UserProfile, UserProfileFilterParams> {

	@Serial
	private static final long serialVersionUID = -2080551823386644045L;

	public UserProfileSpec(UserProfileFilterParams params) {
		super(params);
	}

	@Override
	protected List<Predicate> toPredicateList(Root<UserProfile> root, CriteriaBuilder cb) {
		List<Predicate> predicates = super.toPredicateList(root, cb);

		addPredicateLike(predicates, root, cb, getParams().getFirstName(), UserProfile_.FIRST_NAME);
		addPredicateLike(predicates, root, cb, getParams().getMiddleName(), UserProfile_.MIDDLE_NAME);
		addPredicateLike(predicates, root, cb, getParams().getLastName(), UserProfile_.LAST_NAME);

		addPredicateEq(predicates, root, cb, getParams().getDefaultLang(), UserProfile_.DEFAULT_LANG);

		if (StringUtils.hasText(getParams().getEmail())) {
			Join<UserProfile, User> joinUser = getJoin(null, root, UserProfile_.USER);
			addPredicateLike(predicates, joinUser, cb, getParams().getEmail(), User_.EMAIL);
		}

		return predicates;
	}

}