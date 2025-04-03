package com.bank.MQRouter.controller;

import com.bank.MQRouter.model.MessageEntity;
import com.bank.MQRouter.repository.MessageRepository;
import com.bank.MQRouter.service.MessageService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

public class MessageControllerTest {


    @InjectMocks
    private MessageService messageService;

    @Mock
    private MessageRepository messageRepository;

    private MessageEntity message1;
    private MessageEntity message2;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        // Initialisation des entités Message
        message1 = new MessageEntity();
        message1.setId(1L);
        message1.setMessageId("12345");
        message1.setContent("Test Message 1");
        message1.setReceivedAt("2025-04-03T12:00:00");

        message2 = new MessageEntity();
        message2.setId(2L);
        message2.setMessageId("67890");
        message2.setContent("Test Message 2");
        message2.setReceivedAt("2025-04-03T12:05:00");
    }

    @Test
    public void testGetAllMessages() {
        // Préparation des données pour la pagination
        List<MessageEntity> messages = new ArrayList<>();
        messages.add(message1);
        messages.add(message2);
        Pageable pageable = PageRequest.of(0, 2);  // Page 0 avec 2 éléments par page

        // Simuler la page de messages renvoyée par le repository
        Page<MessageEntity> pageMessages = new PageImpl<>(messages, pageable, messages.size());
        when(messageRepository.findAll(pageable)).thenReturn(pageMessages);

        // Appel à la méthode à tester
        Page<MessageEntity> result = messageService.getAllMessages(pageable);

        // Vérification des résultats
        assertNotNull(result);
        assertEquals(2, result.getContent().size());
        assertEquals("12345", result.getContent().get(0).getMessageId());
        assertEquals("67890", result.getContent().get(1).getMessageId());
    }
}
