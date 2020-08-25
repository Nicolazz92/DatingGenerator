package com.velikokhatko.services;

import com.velikokhatko.model.SearchFilter;
import com.velikokhatko.model.User;
import com.velikokhatko.model.enums.BodyType;
import com.velikokhatko.model.enums.Gender;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Set;

@Service
public class FilterSpecificationBuilder {

    public Specification<User> buildSpecification(SearchFilter searchFilter) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.and(
                buildGenderPredicate(root, criteriaBuilder, searchFilter.getGender()),
                buildAgeMinPredicate(root, criteriaBuilder, searchFilter.getAgeMin()),
                buildAgeMaxPredicate(root, criteriaBuilder, searchFilter.getAgeMax()),
                buildHeightMinPredicate(root, criteriaBuilder, searchFilter.getHeightMin()),
                buildHeightMaxPredicate(root, criteriaBuilder, searchFilter.getHeightMax()),
                buildBodyTypesPredicate(root, criteriaBuilder, searchFilter.getBodyTypes())
        );
    }

    private Predicate buildGenderPredicate(Root<User> root, CriteriaBuilder criteriaBuilder, Gender gender) {
        if (gender == null) {
            return criteriaBuilder.and();
        } else {
            return criteriaBuilder.equal(root.get("gender"), gender);
        }
    }

    private Predicate buildAgeMinPredicate(Root<User> root, CriteriaBuilder criteriaBuilder, Integer ageMin) {
        if (ageMin == null) {
            return criteriaBuilder.and();
        } else {
            return criteriaBuilder.greaterThanOrEqualTo(root.get("age"), ageMin);
        }
    }

    private Predicate buildAgeMaxPredicate(Root<User> root, CriteriaBuilder criteriaBuilder, Integer ageMax) {
        if (ageMax == null) {
            return criteriaBuilder.and();
        } else {
            return criteriaBuilder.lessThanOrEqualTo(root.get("age"), ageMax);
        }
    }

    private Predicate buildHeightMinPredicate(Root<User> root, CriteriaBuilder criteriaBuilder, Double heightMin) {
        if (heightMin == null) {
            return criteriaBuilder.and();
        } else {
            return criteriaBuilder.greaterThanOrEqualTo(root.get("height"), heightMin);
        }
    }

    private Predicate buildHeightMaxPredicate(Root<User> root, CriteriaBuilder criteriaBuilder, Double heightMax) {
        if (heightMax == null) {
            return criteriaBuilder.and();
        } else {
            return criteriaBuilder.lessThanOrEqualTo(root.get("height"), heightMax);
        }
    }

    private Predicate buildBodyTypesPredicate(Root<User> root, CriteriaBuilder criteriaBuilder, Set<BodyType> bodyTypes) {
        if (bodyTypes == null || bodyTypes.size() == 0) {
            return criteriaBuilder.and();
        } else if (bodyTypes.size() == 1 && bodyTypes.iterator().hasNext()) {
            return criteriaBuilder.equal(root.get("bodyType"), bodyTypes.iterator().next());
        } else {
            return root.get("bodyType").in(bodyTypes);
        }
    }
}
