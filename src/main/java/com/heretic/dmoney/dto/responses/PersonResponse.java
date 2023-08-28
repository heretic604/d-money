package com.heretic.dmoney.dto.responses;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.heretic.dmoney.enums.UserRole;
import com.heretic.dmoney.enums.UserStatus;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

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
    @JsonInclude(NON_NULL)
    private List<WalletResponse> walletResponses;
}