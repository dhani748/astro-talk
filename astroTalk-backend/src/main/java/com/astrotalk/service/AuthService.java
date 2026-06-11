package com.astrotalk.service;

import com.astrotalk.dto.*;

public interface AuthService {

    AuthResponse registerUser(RegisterRequest request);

    AuthResponse registerAstrologer(RegisterAstrologerRequest request);

    AuthResponse login(LoginRequest request);
}
