package com.techcore.auth_service.keycloak.dto.response;

import com.techcore.auth_service.keycloak.entity.ContactId;
import lombok.Data;

@Data
public class ContactResponse {
    private ContactId contactId;
    private String contactName;
    private String contactSurname;
    private String contactPhoneNumber;
    private Boolean isBanned;
}
