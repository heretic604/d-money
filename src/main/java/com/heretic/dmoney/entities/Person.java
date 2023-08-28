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

    @Column(name = "USERNAME", unique = true)
    private String username;

    @Column(name = "PASSWORD")
    private String password;

    @Column(name = "EMAIL", unique = true)
    private String email;

    @Enumerated(STRING)
    @Column(name = "USER_STATUS")
    private UserStatus status;

    @Enumerated(STRING)
    @Column(name = "USER_ROLE")
    private UserRole role;

    @Column(name = "BIRTHDAY")
    private LocalDate birthday;

    @Column(name = "REGISTRATION")
    private LocalDateTime registrationTime;

    @OneToMany(mappedBy = "person", fetch = EAGER, cascade = CascadeType.ALL)
    private List<Wallet> wallets;
}