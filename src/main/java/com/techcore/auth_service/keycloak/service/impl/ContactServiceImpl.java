package com.techcore.auth_service.keycloak.service.impl;

import com.techcore.auth_service.keycloak.dto.filter.ContactFilter;
import com.techcore.auth_service.keycloak.dto.filter.ContactSpecification;
import com.techcore.auth_service.keycloak.dto.mapper.ContactMapper;
import com.techcore.auth_service.keycloak.dto.request.CreateContactRequest;
import com.techcore.auth_service.keycloak.dto.response.ContactResponse;
import com.techcore.auth_service.keycloak.dto.response.PageResponse;
import com.techcore.auth_service.keycloak.entity.Contact;
import com.techcore.auth_service.keycloak.entity.ContactId;
import com.techcore.auth_service.keycloak.entity.User;
import com.techcore.auth_service.keycloak.exception.*;
import com.techcore.auth_service.keycloak.repository.ContactRepository;
import com.techcore.auth_service.keycloak.repository.UserRepository;
import com.techcore.auth_service.keycloak.service.ContactService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class ContactServiceImpl implements ContactService {

    private final ContactRepository contactRepository;
    private final UserRepository userRepository;
    private final ContactMapper contactMapper;

    @Override
    public ContactResponse createContact(CreateContactRequest request) {
        ContactId contactId = new ContactId(request.getOwnerId(), request.getTargetId());
        if(contactRepository.existsByContactId(contactId)) throw new ContactAlreadyExistsException();
        if(request.getOwnerId().equals(request.getTargetId())) {
            throw new AddingYourselfIsProhibitedException();
        }

        User ownerUser = userRepository.findById(request.getOwnerId()).orElseThrow(() -> new UserNotFoundException(request.getOwnerId()));
        User targetUser = userRepository.findById(request.getTargetId()).orElseThrow(() -> new UserNotFoundException(request.getTargetId()));

        Contact contact= new Contact(
                contactId,
                ownerUser,
                targetUser,
                false
        );

        contactRepository.save(contact);

        return contactMapper.toResponse(contact);
    }



    @Override
    public PageResponse<ContactResponse> getAllContacts(ContactFilter contactFilter, int page, int size) {

        Specification<Contact> contactSpecification = ContactSpecification.findByTargetUser(contactFilter);
        Page<Contact> contactPage = contactRepository.findAll(
                contactSpecification,
                PageRequest.of(page, size));

        List<ContactResponse> contactContents = contactPage.getContent()
                .stream()
                .map(contactMapper::toResponse)
                .toList();

        PageResponse<ContactResponse> pageResponse = new PageResponse<>(contactContents, page, size, contactContents.size());

        return pageResponse;
    }

    @Override
    public List<ContactResponse> getUserContacts(UUID userId) {

        User requiredUser = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));

        List<Contact> userContacts = contactRepository.findByOwner(requiredUser);

        return userContacts
                .stream()
                .map(contactMapper::toResponse)
                .toList();
    }

    @Override
    public PageResponse<ContactResponse> getAllBannedContacts(ContactFilter filter, int page, int size, boolean isBanned) {
        Specification<Contact> contactSpecification = Specification
                .where(ContactSpecification.findByBanned(isBanned))
                .and(ContactSpecification.findByTargetUser(filter));

        Page<Contact> contactPage = contactRepository.findAll(
                contactSpecification,
                PageRequest.of(page, size));

        List<ContactResponse> contactContents = contactPage
                .stream()
                .map(contactMapper:: toResponse)
                .toList();

        PageResponse<ContactResponse> userResponsePageResponse = new PageResponse<>(contactContents, page, size, contactContents.size());

        return userResponsePageResponse;
    }

    @Override
    public List<ContactResponse> getBannedUserContacts(UUID userId) {

       userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));

        List<Contact> bannedUserContacts = contactRepository.findByOwnerWhereIsBlocked(userId);

        return bannedUserContacts
                .stream()
                .map(contactMapper::toResponse)
                .toList();
    }

    @Override
    public List<ContactResponse> getUnbannedUserContacts(UUID userId) {

        userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));

        List<Contact> unbannedUserContacts = contactRepository.findByOwnerWhereIsUnblocked(userId);

        return unbannedUserContacts
                .stream()
                .map(contactMapper::toResponse)
                .toList();
    }


    @Override
    public ContactResponse getContactById(ContactId contactId) {
       Contact contact = contactRepository.findByContactId(contactId).orElseThrow(() -> new ContantNotFoundException(contactId));

        return contactMapper.toResponse(contact);
    }

    @Override
    @Transactional
    public boolean deleteUserContact(ContactId contactId) {
        Contact contact = contactRepository.findById(contactId).orElseThrow(() -> new ContantNotFoundException(contactId));

        contactRepository.delete(contact);
        return true;
    }

    @Override
    @Transactional
    public ContactResponse blockContact(ContactId contactId) {
        Contact contact = contactRepository.findById(contactId).orElseThrow(() -> new ContantNotFoundException(contactId));

        if(contact.getIsBanned()) throw new ContactAlreadyBannedException(contactId.getTargetId());
        contact.setIsBanned(true);

        return contactMapper.toResponse(contact);
    }

    @Override
    @Transactional
    public ContactResponse unblockContact(ContactId contactId) {
        Contact contact = contactRepository.findById(contactId).orElseThrow(() -> new ContantNotFoundException(contactId));

        if(!contact.getIsBanned()) throw new ContactAlreadyUnbannedException(contactId.getTargetId());
        contact.setIsBanned(false);

        return contactMapper.toResponse(contact);
    }

}
