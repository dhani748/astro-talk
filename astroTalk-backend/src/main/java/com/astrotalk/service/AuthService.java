package com.astrotalk.service;

import com.astrotalk.model.*;

public interface AuthService {

    AuthResponseModel registerUser(RegisterRequestModel request);

    AuthResponseModel registerAstrologer(RegisterAstrologerRequestModel request);

    AuthResponseModel login(LoginRequestModel request);

    AuthResponseModel refreshToken(RefreshTokenModel request);

    void logout(String token);

    void forgetPassword(String email);

    void resetPassword(String token, String password, String confirmPassword);
}
