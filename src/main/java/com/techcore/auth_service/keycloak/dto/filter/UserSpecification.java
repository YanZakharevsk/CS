package com.techcore.auth_service.keycloak.dto.filter;

import com.techcore.auth_service.keycloak.entity.User;
import org.springframework.data.jpa.domain.Specification;

public class UserSpecification {

    private final static String NAME = "name";
    private final static String SURNAME = "surname";
    private final static String PHONE_NUMBER = "phoneNumber";

    public static Specification<User> filterBy(UserFilter userFilter){
        return Specification
                .where(hasName(userFilter.name()))
                .and(hasSurname(userFilter.surname()))
                .and(hasPhoneNumber(userFilter.phoneNumber()));


    }

    private static Specification<User> hasName(String name) {
        return (
                ((root, query, criteriaBuilder) ->
                        name == null || name.isEmpty() ? criteriaBuilder.conjunction():
                        criteriaBuilder.like(criteriaBuilder.lower(root.get(NAME)), "%" + name.toLowerCase() + "%"))

                );
    }

    private static Specification<User> hasSurname(String surname) {
        return (
                ((root, query, criteriaBuilder) ->
                        surname == null || surname.isEmpty()  ? criteriaBuilder.conjunction():
                        criteriaBuilder.like(criteriaBuilder.lower(root.get(SURNAME)), "%" + surname.toLowerCase() + "%"))
                );
    }

    private static Specification<User> hasPhoneNumber(String phoneNumber) {
        return (
                ((root, query, criteriaBuilder) ->
                      phoneNumber == null || phoneNumber.isEmpty()  ? criteriaBuilder.conjunction():
                              criteriaBuilder.like(root.get(PHONE_NUMBER), "%" + phoneNumber + "%"))
                );
    }
}
