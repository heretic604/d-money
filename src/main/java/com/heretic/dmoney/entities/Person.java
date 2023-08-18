package com.heretic.dmoney.entities;

import com.heretic.dmoney.enums.UserRole;
import com.heretic.dmoney.enums.UserStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static javax.persistence.EnumType.STRING;
import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "PERSON")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Person {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "PERSON_ID", unique = true, updatable = false, nullable = false)
    private long userId;

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

    @Column(name = "BIRTHDAY", nullable = false)
    private LocalDate birthday;

    @Column(name = "REGISTRATION", nullable = false)
    private LocalDateTime registrationTime;
}