package com.techcore.auth_service.keycloak.dto.mapper;

import com.techcore.auth_service.keycloak.dto.response.ContactResponse;
import com.techcore.auth_service.keycloak.entity.Contact;
import com.techcore.auth_service.keycloak.entity.User;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-10-02T21:43:12+0300",
    comments = "version: 1.6.3, compiler: javac, environment: Java 21.0.12.1 (Microsoft)"
)
@Component
public class ContactMapperImpl implements ContactMapper {

    @Override
    public ContactResponse toResponse(Contact contact) {
        if ( contact == null ) {
            return null;
        }

        ContactResponse contactResponse = new ContactResponse();

        contactResponse.setContactName( contactTargetName( contact ) );
        contactResponse.setContactSurname( contactTargetSurname( contact ) );
        contactResponse.setContactPhoneNumber( contactTargetPhoneNumber( contact ) );
        contactResponse.setIsBanned( contact.getIsBanned() );
        contactResponse.setContactId( contact.getContactId() );

        return contactResponse;
    }

    private String contactTargetName(Contact contact) {
        User target = contact.getTarget();
        if ( target == null ) {
            return null;
        }
        return target.getName();
    }

    private String contactTargetSurname(Contact contact) {
        User target = contact.getTarget();
        if ( target == null ) {
            return null;
        }
        return target.getSurname();
    }

    private String contactTargetPhoneNumber(Contact contact) {
        User target = contact.getTarget();
        if ( target == null ) {
            return null;
        }
        return target.getPhoneNumber();
    }
}
