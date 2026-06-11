package com.astrotalk.service;

import com.astrotalk.entity.AstrologerStatus;

public interface AstrologerStatusService {

    AstrologerStatus toggleOnlineStatus(Long astrologerId);

    void setStatus(Long astrologerId, AstrologerStatus status);
}
