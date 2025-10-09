package com.paradigma.filmio.core.service;

import com.paradigma.filmio.core.model.MessageContent;
import org.springframework.stereotype.Service;


@Service
public class DefaultMessageGenerator implements MessageGenerator{
    @Override
    public MessageContent generateEmailVerificationTokenMessage(String receiverEmail, String token) {
        return null;
    }

    @Override
    public MessageContent generatePasswordResetTokenMessage(String receiverEmail, String token) {
        return null;
    }
}
