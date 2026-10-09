package com.techcore.auth_service.keycloak.repository;

import com.techcore.auth_service.keycloak.entity.Contact;
import com.techcore.auth_service.keycloak.entity.ContactId;
import com.techcore.auth_service.keycloak.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ContactRepository extends JpaRepository<Contact, ContactId>, JpaSpecificationExecutor<Contact> {
    boolean getContactByContactId(ContactId contactId);

    boolean existsByContactId(ContactId contactId);

    List<Contact> findByOwner(User owner);

    Optional<Contact> findByContactId(ContactId contactId);

    List<Contact> findByIsBanned(Boolean isBanned);


    @Query(value = "SELECT * FROM user_contacts WHERE owner_id = :ownerId AND banned = true", nativeQuery = true)
    List<Contact> findByOwnerWhereIsBlocked( UUID ownerId);

    @Query(value = "SELECT * FROM user_contacts WHERE owner_id = :ownerId AND banned = false", nativeQuery = true)
    List<Contact> findByOwnerWhereIsUnblocked(UUID ownerId);

}
