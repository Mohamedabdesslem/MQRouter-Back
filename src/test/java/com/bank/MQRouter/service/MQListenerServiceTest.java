package com.bank.MQRouter.service;

import com.bank.MQRouter.repository.MessageRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import javax.jms.TextMessage;

import static org.mockito.Mockito.*;

class MQListenerServiceTest {

    @Mock
    private MessageRepository messageRepository;

    @InjectMocks
    private MQListenerService mqListenerService;

    @Mock
    private TextMessage textMessage;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);  // Initialise les mocks
    }

    @Test
    void testOnMessage() throws Exception {
        // Préparer un message simulé
        String expectedMessageContent = "Test JMS message";
        when(textMessage.getText()).thenReturn(expectedMessageContent);

        // Appeler la méthode onMessage
        mqListenerService.onMessage(textMessage);

        // Vérifier que le repository a bien sauvegardé le message
        verify(messageRepository, times(1)).save(argThat(msg -> msg.getContent().equals(expectedMessageContent)));
    }
}
