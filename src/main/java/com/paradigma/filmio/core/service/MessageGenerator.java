package com.paradigma.filmio.core.service;

import com.paradigma.filmio.core.model.MessageContent;

public interface MessageGenerator {
    MessageContent generateEmailVerificationTokenMessage(String receiverEmail, String token);
    MessageContent generatePasswordResetTokenMessage(String receiverEmail, String token);
}
