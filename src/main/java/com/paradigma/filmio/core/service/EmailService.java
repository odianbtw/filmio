package com.paradigma.filmio.core.service;

import com.paradigma.filmio.core.model.MessageContent;

public interface EmailService {
    void sendEmail (MessageContent content);
}
