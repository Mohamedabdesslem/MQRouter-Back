package com.bank.MQRouter.controller;

import com.bank.MQRouter.model.MessageEntity;
import com.bank.MQRouter.service.MessageService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/messages")
public class MessageController {

    private final MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @GetMapping
    public Page<MessageEntity> getAllMessages(Pageable pageable) {
        return messageService.getAllMessages(pageable);
    }
}
