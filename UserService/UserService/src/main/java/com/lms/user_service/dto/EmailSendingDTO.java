package com.lms.user_service.dto;

public record EmailSendingDTO(String email,
                              String subject,
                              String body) {
}
