package com.heretic.dmoney.dto.requests;

import com.heretic.dmoney.entities.Wallet;
import com.heretic.dmoney.enums.UserRole;
import com.heretic.dmoney.enums.UserStatus;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@Data
@Builder
public class PersonRequest {

    private String username;
    private String password;
    private String email;
    private UserStatus status;
    private UserRole role;
    private LocalDate birthday;
    private LocalDateTime registrationTime;
    private Set<Wallet> wallets;
}