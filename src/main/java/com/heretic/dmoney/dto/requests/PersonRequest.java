package com.heretic.dmoney.dto.requests;

import com.heretic.dmoney.enums.UserRole;
import com.heretic.dmoney.enums.UserStatus;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static com.heretic.dmoney.util.Constants.*;

@Data
@Builder
public class PersonRequest {

    @NotBlank(message = INVALID_USERNAME)
    private String username;
    @NotBlank(message = INVALID_PASSWORD)
    private String password;
    @Email(message = INVALID_EMAIL)
    private String email;
    private UserStatus status;
    private UserRole role;
    private LocalDate birthday;
    private LocalDateTime registrationTime;
}