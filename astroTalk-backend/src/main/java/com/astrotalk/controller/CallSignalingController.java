package com.astrotalk.controller;

import com.astrotalk.entity.ConsultationStatus;
import com.astrotalk.model.CallInviteModel;
import com.astrotalk.model.SignalModel;
import com.astrotalk.repository.ConsultationRepository;
import com.astrotalk.service.ConsultationService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;

import java.security.Principal;

/**
 * WebSocket controller for WebRTC call signaling between users and astrologers.
 * Handles invite, accept, reject, offer, answer, ICE candidate, and end-call events.
 */
@Controller
@RequiredArgsConstructor
public class CallSignalingController {

    private final SimpMessageSendingOperations messagingTemplate;
    private final ConsultationService consultationService;
    private final ConsultationRepository consultationRepository;

    /**
     * Handles /call.invite - Sends a call invitation to an astrologer for an active consultation.
     *
     * @param invite        the call invite details (consultation ID, caller info)
     * @param headerAccessor used to extract the authenticated principal
     */
    @MessageMapping("/call.invite")
    public void invite(@Payload CallInviteModel invite, SimpMessageHeaderAccessor headerAccessor) {
        Principal principal = headerAccessor.getUser();
        if (principal == null) return;

        var consultation = consultationRepository.findById(invite.getConsultationId());
        if (consultation.isEmpty() || consultation.get().getStatus() != ConsultationStatus.ACTIVE) return;

        Long astrologerId = consultation.get().getAstrologer().getId();
        messagingTemplate.convertAndSend("/topic/user/" + astrologerId + "/call", invite);
    }

    /**
     * Handles /call.accept - Forwards an accept signal to the consultation topic.
     *
     * @param signal the signal payload containing consultation ID and SDP data
     */
    @MessageMapping("/call.accept")
    public void accept(@Payload SignalModel signal) {
        messagingTemplate.convertAndSend(
                "/topic/consultation/" + signal.getConsultationId() + "/signal",
                signal);
    }

    /**
     * Handles /call.reject - Forwards a reject signal to the consultation topic.
     *
     * @param signal the signal payload containing consultation ID
     */
    @MessageMapping("/call.reject")
    public void reject(@Payload SignalModel signal) {
        messagingTemplate.convertAndSend(
                "/topic/consultation/" + signal.getConsultationId() + "/signal",
                signal);
    }

    /**
     * Handles /call.offer - Forwards a WebRTC SDP offer to the consultation topic.
     *
     * @param signal the signal payload containing the SDP offer
     */
    @MessageMapping("/call.offer")
    public void offer(@Payload SignalModel signal) {
        messagingTemplate.convertAndSend(
                "/topic/consultation/" + signal.getConsultationId() + "/signal",
                signal);
    }

    /**
     * Handles /call.answer - Forwards a WebRTC SDP answer to the consultation topic.
     *
     * @param signal the signal payload containing the SDP answer
     */
    @MessageMapping("/call.answer")
    public void answer(@Payload SignalModel signal) {
        messagingTemplate.convertAndSend(
                "/topic/consultation/" + signal.getConsultationId() + "/signal",
                signal);
    }

    /**
     * Handles /call.ice - Forwards an ICE candidate to the consultation topic for NAT traversal.
     *
     * @param signal the signal payload containing the ICE candidate
     */
    @MessageMapping("/call.ice")
    public void ice(@Payload SignalModel signal) {
        messagingTemplate.convertAndSend(
                "/topic/consultation/" + signal.getConsultationId() + "/signal",
                signal);
    }

    /**
     * Handles /call.end - Ends an active consultation and notifies participants.
     *
     * @param signal the signal payload containing the consultation ID to end
     */
    @MessageMapping("/call.end")
    public void endCall(@Payload SignalModel signal) {
        consultationService.endConsultation(signal.getConsultationId());
        messagingTemplate.convertAndSend(
                "/topic/consultation/" + signal.getConsultationId() + "/signal",
                signal);
    }
}
