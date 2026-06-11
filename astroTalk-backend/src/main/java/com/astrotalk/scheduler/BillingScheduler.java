package com.astrotalk.scheduler;

import com.astrotalk.entity.*;
import com.astrotalk.exception.InsufficientBalanceException;
import com.astrotalk.repository.AstrologerRepository;
import com.astrotalk.repository.ConsultationRepository;
import com.astrotalk.service.WalletService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class BillingScheduler {

    private final ConsultationRepository consultationRepository;
    private final AstrologerRepository astrologerRepository;
    private final WalletService walletService;

    @Scheduled(fixedRate = 60000)
    @Transactional
    public void processActiveConsultations() {
        List<Consultation> activeConsultations = consultationRepository
                .findByStatus(ConsultationStatus.ACTIVE);

        for (Consultation consultation : activeConsultations) {
            processConsultation(consultation);
        }
    }

    private void processConsultation(Consultation consultation) {
        Astrologer astrologer = consultation.getAstrologer();
        BigDecimal pricePerMinute = astrologer.getConsultationFee();

        if (pricePerMinute == null || pricePerMinute.compareTo(BigDecimal.ZERO) <= 0) {
            endConsultation(consultation, "Astrologer fee not configured");
            return;
        }

        BigDecimal astrologerShare = pricePerMinute.multiply(BigDecimal.valueOf(0.7));
        BigDecimal platformShare = pricePerMinute.subtract(astrologerShare);

        try {
            walletService.deductBalance(
                    consultation.getUser().getId(),
                    pricePerMinute,
                    "Consultation minute charge - " + astrologer.getName()
            );
        } catch (InsufficientBalanceException e) {
            endConsultation(consultation, "Low balance - consultation ended automatically");
            return;
        }

        astrologer.setTotalEarnings(astrologer.getTotalEarnings().add(astrologerShare));
        astrologerRepository.save(astrologer);

        consultation.setDurationMinutes(
                (consultation.getDurationMinutes() == null ? 0 : consultation.getDurationMinutes()) + 1);
        consultation.setTotalAmount(
                (consultation.getTotalAmount() == null ? BigDecimal.ZERO : consultation.getTotalAmount()).add(pricePerMinute));
        consultation.setAstrologerEarning(
                (consultation.getAstrologerEarning() == null ? BigDecimal.ZERO : consultation.getAstrologerEarning()).add(astrologerShare));
        consultation.setPlatformCommission(
                (consultation.getPlatformCommission() == null ? BigDecimal.ZERO : consultation.getPlatformCommission()).add(platformShare));
        consultationRepository.save(consultation);
    }

    private void endConsultation(Consultation consultation, String reason) {
        consultation.setStatus(ConsultationStatus.CANCELLED);
        consultation.setEndTime(LocalDateTime.now());
        consultationRepository.save(consultation);

        Astrologer astrologer = consultation.getAstrologer();
        astrologer.setStatus(AstrologerStatus.ONLINE);
        astrologer.setAvailable(true);
        astrologerRepository.save(astrologer);

        log.warn("Consultation {} ended: {}", consultation.getId(), reason);
    }
}
