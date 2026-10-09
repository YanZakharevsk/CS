package com.techcore.auth_service.keycloak.dto.filter;

import com.techcore.auth_service.keycloak.entity.Contact;
import com.techcore.auth_service.keycloak.entity.User;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;


public class ContactSpecification {

    private final static String NAME = "name";
    private final static String SURNAME = "surname";
    private final static String PHONE_NUMBER = "phoneNumber";
    private final static String BANNED = "isBanned";

    public static Specification<Contact> findByTargetUser(ContactFilter contactFilter){

        return (root, query, criteriaBuilder) -> {
            Join<Contact, User> targetUserJoin = root.join("target");


            List<Predicate> predicates = new ArrayList<>();

            if(contactFilter.contactName() != null && !contactFilter.contactName().isBlank())
            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(targetUserJoin.get(NAME)), "%" + contactFilter.contactName().toLowerCase() + "%"));

            if(contactFilter.contactSurname() != null && !contactFilter.contactSurname().isBlank())
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(targetUserJoin.get(SURNAME)), "%" + contactFilter.contactSurname().toLowerCase() + "%"));

            if(contactFilter.contactPhoneNumber() != null && !contactFilter.contactPhoneNumber().isBlank())
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(targetUserJoin.get(PHONE_NUMBER)), "%" + contactFilter.contactPhoneNumber().toLowerCase() + "%"));

            return criteriaBuilder.and(predicates.toArray(new  Predicate[0] ));
        };

    }

    public static Specification<Contact> findByBanned(boolean isBanned){
        return ((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(BANNED), isBanned));
    }








}
