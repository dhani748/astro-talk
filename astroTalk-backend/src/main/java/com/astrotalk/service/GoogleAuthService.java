package com.astrotalk.service;

import com.astrotalk.dto.AuthResponse;
import com.astrotalk.dto.GoogleLoginRequest;

public interface GoogleAuthService {
    AuthResponse authenticate(GoogleLoginRequest request);
}
