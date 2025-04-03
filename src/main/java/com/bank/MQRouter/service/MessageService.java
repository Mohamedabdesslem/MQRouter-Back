package com.bank.MQRouter.service;

import com.bank.MQRouter.model.MessageEntity;
import com.bank.MQRouter.repository.MessageRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class MessageService {

    private final MessageRepository messageRepository;

    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    public Page<MessageEntity> getAllMessages(Pageable pageable) {
        return messageRepository.findAll(pageable);
    }
}
