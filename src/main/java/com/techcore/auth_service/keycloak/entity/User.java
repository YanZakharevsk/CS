package com.techcore.auth_service.keycloak.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.proxy.HibernateProxy;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "users")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class User extends BaseAuditableEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(nullable = false, unique = true)
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String surname;

    @Column(nullable = false, updatable = false, unique = true)
    private String email;

    @Column(name = "active", nullable = false)
    private Boolean isActive;

    @Column(name = "phone_number", nullable = false, updatable = false, unique = true)
    private String phoneNumber;

    @Column(name = "birth_date", nullable = false)
    private LocalDate birthDate;

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "owner", orphanRemoval = true, cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<Contact> contacts;

    public User(String name, String surname, String email, Boolean isActive, String phoneNumber, LocalDate birthDate, List<Contact> contacts) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.isActive = isActive;
        this.phoneNumber = phoneNumber;
        this.birthDate = birthDate;
        this.contacts = contacts;
    }

    @Override
    public final boolean equals(Object o) {
        if(this == o) return true;
        if(o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy
                ?((HibernateProxy) o).getHibernateLazyInitializer()
                .getPersistentClass()
                : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy
                ?((HibernateProxy) this).getHibernateLazyInitializer()
                .getPersistentClass()
                : this.getClass();
        if(thisEffectiveClass != oEffectiveClass) return false;
        User user = (User) o;
        return getId() != null && Objects.equals(getId(), user.getId());
    }

    @Override
    public int hashCode() {
        return this instanceof HibernateProxy
                ? ((HibernateProxy) this).getHibernateLazyInitializer()
                .getPersistentClass()
                .hashCode()
                : getClass().hashCode();

    }

}
