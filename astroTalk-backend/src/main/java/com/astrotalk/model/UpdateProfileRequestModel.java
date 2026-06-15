package com.astrotalk.model;

import com.astrotalk.entity.Gender;
import lombok.Data;

import java.time.LocalDate;

@Data
public class UpdateProfileRequestModel {
    private String name;
    private String phone;
    private LocalDate dateOfBirth;
    private String placeOfBirth;
    private Gender gender;
    private String profilePicture;
}
