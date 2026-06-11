package com.astrotalk.controller;

import com.astrotalk.dto.CallInviteDTO;
import com.astrotalk.dto.SignalDTO;
import com.astrotalk.entity.ConsultationStatus;
import com.astrotalk.repository.ConsultationRepository;
import com.astrotalk.service.ConsultationService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class CallSignalingController {

    private final SimpMessageSendingOperations messagingTemplate;
    private final ConsultationService consultationService;
    private final ConsultationRepository consultationRepository;

    @MessageMapping("/call.invite")
    public void invite(@Payload CallInviteDTO invite, SimpMessageHeaderAccessor headerAccessor) {
        Principal principal = headerAccessor.getUser();
        if (principal == null) return;

        var consultation = consultationRepository.findById(invite.getConsultationId());
        if (consultation.isEmpty() || consultation.get().getStatus() != ConsultationStatus.ACTIVE) return;

        Long astrologerId = consultation.get().getAstrologer().getId();
        messagingTemplate.convertAndSend("/topic/user/" + astrologerId + "/call", invite);
    }

    @MessageMapping("/call.accept")
    public void accept(@Payload SignalDTO signal) {
        messagingTemplate.convertAndSend(
                "/topic/consultation/" + signal.getConsultationId() + "/signal",
                signal);
    }

    @MessageMapping("/call.reject")
    public void reject(@Payload SignalDTO signal) {
        messagingTemplate.convertAndSend(
                "/topic/consultation/" + signal.getConsultationId() + "/signal",
                signal);
    }

    @MessageMapping("/call.offer")
    public void offer(@Payload SignalDTO signal) {
        messagingTemplate.convertAndSend(
                "/topic/consultation/" + signal.getConsultationId() + "/signal",
                signal);
    }

    @MessageMapping("/call.answer")
    public void answer(@Payload SignalDTO signal) {
        messagingTemplate.convertAndSend(
                "/topic/consultation/" + signal.getConsultationId() + "/signal",
                signal);
    }

    @MessageMapping("/call.ice")
    public void ice(@Payload SignalDTO signal) {
        messagingTemplate.convertAndSend(
                "/topic/consultation/" + signal.getConsultationId() + "/signal",
                signal);
    }

    @MessageMapping("/call.end")
    public void endCall(@Payload SignalDTO signal) {
        consultationService.endConsultation(signal.getConsultationId());
        messagingTemplate.convertAndSend(
                "/topic/consultation/" + signal.getConsultationId() + "/signal",
                signal);
    }
}
