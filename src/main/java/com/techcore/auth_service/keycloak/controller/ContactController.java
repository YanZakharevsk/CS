package com.techcore.auth_service.keycloak.controller;

import com.techcore.auth_service.keycloak.dto.filter.ContactFilter;
import com.techcore.auth_service.keycloak.dto.request.CreateContactRequest;
import com.techcore.auth_service.keycloak.dto.response.ContactResponse;
import com.techcore.auth_service.keycloak.dto.response.PageResponse;
import com.techcore.auth_service.keycloak.entity.ContactId;
import com.techcore.auth_service.keycloak.service.ContactService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users/contacts")
public class ContactController {
    private final ContactService contactService;

    @PostMapping
    public ResponseEntity<ContactResponse> createUserContact(@RequestBody @Valid CreateContactRequest request){
        ContactResponse contactResponse = contactService.createContact(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(contactResponse);
    }

    //ADMIN
    @GetMapping
    public ResponseEntity<PageResponse<ContactResponse>> getAllContact(@RequestParam(required = false) String contactName,
                                                                       @RequestParam(required = false) String contactSurname,
                                                                       @RequestParam(required = false) String contactPhoneNumber,
                                                                       @Min(value = 0,message = "Page number can not less than 0")
                                                                       @RequestParam(defaultValue = "0") int page,
                                                                       @Min(value = 1, message = "Size number can not less than 1")
                                                                           @Max(value = 100, message = "Size number can not more than 100")
                                                                           @RequestParam(defaultValue = "10") int size){
        ContactFilter contactFilter = new ContactFilter(contactName, contactSurname, contactPhoneNumber);

        PageResponse<ContactResponse> pageResponse = contactService.getAllContacts(contactFilter, page, size);

        return ResponseEntity.status(HttpStatus.OK)
                .body(pageResponse);
    }

    //ADMIN
    @GetMapping("/blocked")
    public ResponseEntity<PageResponse<ContactResponse>> getTheWholeBannedContacts(@RequestParam(required = false) String contactName,
                                                                           @RequestParam(required = false) String contactSurname,
                                                                           @RequestParam(required = false) String contactPhoneNumber,
                                                                           @Min(value = 0,message = "Page number can not less than 0")
                                                                               @RequestParam(defaultValue = "0") int page,
                                                                           @Min(value = 1, message = "Size number can not less than 1")
                                                                               @Max(value = 100, message = "Size number can not more than 100")
                                                                               @RequestParam(defaultValue = "10") int size){

        ContactFilter contactFilter = new ContactFilter(contactName, contactSurname, contactPhoneNumber);

        PageResponse<ContactResponse> contactResponses = contactService.getAllBannedContacts(contactFilter, page, size, true);

        return ResponseEntity.status(HttpStatus.OK)
                .body(contactResponses);
    }

    //ADMIN
    @GetMapping("/unblocked")
    public ResponseEntity<PageResponse<ContactResponse>> getTheWholeUnbannedContacts(@RequestParam(required = false) String contactName,
                                                                             @RequestParam(required = false) String contactSurname,
                                                                             @RequestParam(required = false) String contactPhoneNumber,
                                                                             @Min(value = 0,message = "Page number can not less than 0")
                                                                                 @RequestParam(defaultValue = "0") int page,
                                                                             @Min(value = 1, message = "Size number can not less than 1")
                                                                                 @Max(value = 100, message = "Size number can not more than 100")
                                                                                 @RequestParam(defaultValue = "10") int size){
        ContactFilter contactFilter = new ContactFilter(contactName, contactSurname, contactPhoneNumber);

        PageResponse<ContactResponse> contactResponses = contactService.getAllBannedContacts(contactFilter, page, size, false);

        return ResponseEntity.status(HttpStatus.OK)
                .body(contactResponses);
    }

    @GetMapping("/myContacts")
    public ResponseEntity<List<ContactResponse>> getUserContacts(@RequestHeader(name = "userId") UUID userId){
        return ResponseEntity.status(HttpStatus.OK)
                .body(contactService.getUserContacts(userId));

    }

    @GetMapping("/{userId}/blocked")
    public ResponseEntity<List<ContactResponse>> getBlockedUserContacts(@PathVariable("userId") UUID userId){
        List<ContactResponse> blockedUserContacts = contactService.getBannedUserContacts(userId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(blockedUserContacts);
    }

    @GetMapping("/{userId}/unblocked")
    public ResponseEntity<List<ContactResponse>> getUnblockedUserContacts(@PathVariable("userId") UUID userId){
        List<ContactResponse> unblockedUserContacts = contactService.getUnbannedUserContacts(userId);

        return ResponseEntity.status(HttpStatus.OK)
                .body(unblockedUserContacts);
    }

    @GetMapping("/{userId}/{contactId}")
    public ResponseEntity<ContactResponse> getContactById(@PathVariable(name = "userId") UUID userId, @PathVariable(name = "contactId") UUID targetId){
        ContactId contactId = new ContactId(userId, targetId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(contactService.getContactById(contactId));
    }

    @PutMapping("/{userId}/{contactId}/block")
    public ResponseEntity<ContactResponse> blockAContact(@PathVariable(name = "userId") UUID userId, @PathVariable(name = "contactId") UUID targetId){
        ContactId contactId = new ContactId(userId, targetId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(contactService.blockContact(contactId));
    }

    @PutMapping("/{userId}/{contactId}/unblock")
    public ResponseEntity<ContactResponse> unblockAContact(@PathVariable(name = "userId") UUID userId, @PathVariable(name = "contactId") UUID targetId){
        ContactId contactId = new ContactId(userId, targetId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(contactService.unblockContact(contactId));
    }

    @DeleteMapping("/{userId}/{contactId}")
    public ResponseEntity<Void> deleteContact(@PathVariable(name = "userId") UUID userId, @PathVariable(name = "contactId") UUID targetId){
        ContactId contactId = new ContactId(userId, targetId);
        contactService.deleteUserContact(contactId);
        return ResponseEntity.noContent().build();
    }



}
