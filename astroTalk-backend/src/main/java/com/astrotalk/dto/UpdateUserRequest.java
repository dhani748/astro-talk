package com.astrotalk.dto;

import com.astrotalk.entity.Gender;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class UpdateUserRequest {

    private String name;

    private String phone;

    private LocalDate dateOfBirth;

    private LocalTime timeOfBirth;

    private String placeOfBirth;

    private Double latitude;

    private Double longitude;

    private Gender gender;

    private String profilePicture;
}
