package com.techcore.auth_service.keycloak.dto.mapper;

import com.techcore.auth_service.keycloak.dto.response.ContactResponse;
import com.techcore.auth_service.keycloak.entity.Contact;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ContactMapper {
    @Mapping(target = "contactName", source = "contact.target.name")
    @Mapping(target = "contactSurname", source = "contact.target.surname")
    @Mapping(target = "contactPhoneNumber", source = "contact.target.phoneNumber")
    @Mapping(target = "isBanned", source = "contact" +
            ".isBanned")
    ContactResponse toResponse(Contact contact);
}
