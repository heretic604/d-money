package com.heretic.dmoney.dto.responses;

import com.heretic.dmoney.entities.Wallet;
import com.heretic.dmoney.enums.UserRole;
import com.heretic.dmoney.enums.UserStatus;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Data
@Builder
public class PersonResponse {

    private UUID personId;
    private String username;
    private String password;
    private String email;
    private UserStatus status;
    private UserRole role;
    private LocalDate birthday;
    private LocalDateTime registrationTime;
    private Set<Wallet> wallets;
}