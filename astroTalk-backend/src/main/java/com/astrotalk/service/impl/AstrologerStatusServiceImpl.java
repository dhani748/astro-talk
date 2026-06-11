package com.astrotalk.service.impl;

import com.astrotalk.entity.Astrologer;
import com.astrotalk.entity.AstrologerStatus;
import com.astrotalk.exception.ResourceNotFoundException;
import com.astrotalk.repository.AstrologerRepository;
import com.astrotalk.service.AstrologerStatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AstrologerStatusServiceImpl implements AstrologerStatusService {

    private final AstrologerRepository astrologerRepository;

    @Override
    @Transactional
    public AstrologerStatus toggleOnlineStatus(Long astrologerId) {
        Astrologer astrologer = astrologerRepository.findById(astrologerId)
                .orElseThrow(() -> new ResourceNotFoundException("Astrologer", astrologerId));

        AstrologerStatus newStatus = astrologer.getStatus() == AstrologerStatus.ONLINE
                ? AstrologerStatus.OFFLINE
                : AstrologerStatus.ONLINE;

        astrologer.setStatus(newStatus);
        astrologer.setAvailable(newStatus == AstrologerStatus.ONLINE);
        astrologerRepository.save(astrologer);

        return newStatus;
    }

    @Override
    @Transactional
    public void setStatus(Long astrologerId, AstrologerStatus status) {
        Astrologer astrologer = astrologerRepository.findById(astrologerId)
                .orElseThrow(() -> new ResourceNotFoundException("Astrologer", astrologerId));

        astrologer.setStatus(status);
        astrologer.setAvailable(status == AstrologerStatus.ONLINE);
        astrologerRepository.save(astrologer);
    }
}
