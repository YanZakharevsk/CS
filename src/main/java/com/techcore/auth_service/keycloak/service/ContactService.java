package com.techcore.auth_service.keycloak.service;

import com.techcore.auth_service.keycloak.dto.filter.ContactFilter;
import com.techcore.auth_service.keycloak.dto.request.CreateContactRequest;
import com.techcore.auth_service.keycloak.dto.response.ContactResponse;
import com.techcore.auth_service.keycloak.dto.response.PageResponse;
import com.techcore.auth_service.keycloak.entity.ContactId;

import java.util.List;
import java.util.UUID;

public interface ContactService {
    ContactResponse createContact(CreateContactRequest request);

    PageResponse<ContactResponse> getAllContacts(ContactFilter contactFilter, int page, int size);

    List<ContactResponse> getUserContacts(UUID userId);

    PageResponse<ContactResponse> getAllBannedContacts(ContactFilter filter, int page, int size, boolean isBanned);

    List<ContactResponse> getBannedUserContacts(UUID userId);

    List<ContactResponse> getUnbannedUserContacts(UUID userId);

    ContactResponse getContactById(ContactId contactId);

    boolean deleteUserContact(ContactId contactId);

    ContactResponse blockContact(ContactId contactId);

    ContactResponse unblockContact(ContactId contactId);


}
