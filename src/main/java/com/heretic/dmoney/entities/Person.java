package com.heretic.dmoney.entities;

import com.heretic.dmoney.enums.UserRole;
import com.heretic.dmoney.enums.UserStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static jakarta.persistence.CascadeType.ALL;
import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.FetchType.EAGER;

@Entity
@Table(name = "PERSON")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "PERSON_ID")
    private UUID personId;

    @Column(name = "USERNAME", unique = true, nullable = false)
    private String username;

    @Column(name = "PASSWORD", nullable = false)
    private String password;

    @Column(name = "EMAIL", unique = true, nullable = false)
    private String email;

    @Enumerated(STRING)
    @Column(name = "USER_STATUS", nullable = false)
    private UserStatus status;

    @Enumerated(STRING)
    @Column(name = "USER_ROLE", nullable = false)
    private UserRole role;

    @Column(name = "BIRTHDAY", updatable = false, nullable = false)
    private LocalDate birthday;

    @Column(name = "REGISTRATION", updatable = false, nullable = false)
    private LocalDateTime registrationTime;

    @OneToMany(mappedBy = "person", fetch = EAGER, cascade = ALL, orphanRemoval = true)
    private List<Wallet> wallets;
}