package com.velikokhatko.utils;

import com.velikokhatko.model.SearchFilter;
import com.velikokhatko.model.User;
import com.velikokhatko.model.enums.BodyType;
import com.velikokhatko.model.enums.Gender;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Set;

public class FilterSpecificationBuilder {

    public static Specification<User> buildSpecification(SearchFilter searchFilter) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.and(
                buildGenderPredicate(root, criteriaBuilder, searchFilter.getGender()),
                buildAgeMinPredicate(root, criteriaBuilder, searchFilter.getAgeMin()),
                buildAgeMaxPredicate(root, criteriaBuilder, searchFilter.getAgeMax()),
                buildHeightMinPredicate(root, criteriaBuilder, searchFilter.getHeightMin()),
                buildHeightMaxPredicate(root, criteriaBuilder, searchFilter.getHeightMax()),
                buildBodyTypesPredicate(root, criteriaBuilder, searchFilter.getBodyTypes())
        );
    }

    private static Predicate buildGenderPredicate(Root<User> root, CriteriaBuilder criteriaBuilder, Gender gender) {
        if (gender == null) {
            return criteriaBuilder.and();
        } else {
            return criteriaBuilder.equal(root.get("gender"), gender);
        }
    }

    private static Predicate buildAgeMinPredicate(Root<User> root, CriteriaBuilder criteriaBuilder, Integer ageMin) {
        if (ageMin == null) {
            return criteriaBuilder.and();
        } else {
            return criteriaBuilder.greaterThanOrEqualTo(root.get("age"), ageMin);
        }
    }

    private static Predicate buildAgeMaxPredicate(Root<User> root, CriteriaBuilder criteriaBuilder, Integer ageMax) {
        if (ageMax == null) {
            return criteriaBuilder.and();
        } else {
            return criteriaBuilder.lessThanOrEqualTo(root.get("age"), ageMax);
        }
    }

    private static Predicate buildHeightMinPredicate(Root<User> root, CriteriaBuilder criteriaBuilder, Integer heightMin) {
        if (heightMin == null) {
            return criteriaBuilder.and();
        } else {
            return criteriaBuilder.greaterThanOrEqualTo(root.get("height"), heightMin);
        }
    }

    private static Predicate buildHeightMaxPredicate(Root<User> root, CriteriaBuilder criteriaBuilder, Integer heightMax) {
        if (heightMax == null) {
            return criteriaBuilder.and();
        } else {
            return criteriaBuilder.lessThanOrEqualTo(root.get("height"), heightMax);
        }
    }

    private static Predicate buildBodyTypesPredicate(Root<User> root, CriteriaBuilder criteriaBuilder, Set<BodyType> bodyTypes) {
        if (bodyTypes == null || bodyTypes.size() == 0) {
            return criteriaBuilder.and();
        } else if (bodyTypes.size() == 1 && bodyTypes.iterator().hasNext()) {
            return criteriaBuilder.equal(root.get("bodyType"), bodyTypes.iterator().next());
        } else {
            return root.get("bodyType").in(bodyTypes);
        }
    }
}
