package com.astrotalk.service;

import com.astrotalk.model.AuthResponseModel;
import com.astrotalk.model.GoogleLoginRequestModel;

public interface GoogleAuthService {
    AuthResponseModel authenticate(GoogleLoginRequestModel request);
}
