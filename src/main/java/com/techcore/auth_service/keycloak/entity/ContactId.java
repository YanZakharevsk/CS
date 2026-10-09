package com.techcore.auth_service.keycloak.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Table;
import lombok.*;

import java.io.Serializable;
import java.util.UUID;


@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContactId implements Serializable {

    @Column(name = "owner_id")
    private UUID ownerId;

    @Column(name = "target_id")
    private UUID targetId;
}
