package com.astrotalk.model;

import com.astrotalk.entity.Role;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthResponseModel {
    private String token;
    private Long id;
    private String name;
    private String email;
    private Role role;
}
