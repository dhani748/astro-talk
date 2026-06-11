package com.astrotalk.dto;

import com.astrotalk.entity.Gender;
import com.astrotalk.entity.Role;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@Builder
public class UserResponse {

    private Long id;
    private String name;
    private String email;
    private String phone;
    private LocalDate dateOfBirth;
    private LocalTime timeOfBirth;
    private String placeOfBirth;
    private Double latitude;
    private Double longitude;
    private Gender gender;
    private String profilePicture;
    private Role role;
    private boolean emailVerified;
    private boolean active;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
