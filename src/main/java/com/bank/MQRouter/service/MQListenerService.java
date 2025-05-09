package com.bank.MQRouter.service;

import com.bank.MQRouter.model.MessageEntity;
import com.bank.MQRouter.repository.MessageRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.TextMessage;
import java.time.LocalDateTime;

@Service
@Slf4j
public class MQListenerService {

    private final MessageRepository messageRepository;


    public MQListenerService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    /**
     * Méthode appelée lorsqu'un message arrive dans la queue IBM MQ.
     * Cette méthode lit le message et le sauvegarde dans la base de données.
     *
     * @param message le message reçu
     * @throws JMSException si une erreur survient lors de la lecture du message
     */
    @JmsListener(destination = "${mq.queue}", containerFactory = "jmsListenerContainerFactory")
    public void onMessage(Message message) throws JMSException {
        if (message instanceof TextMessage) {
            // Récupère le contenu du message
            String messageText = ((TextMessage) message).getText();

            // Log pour débogage
            log.info("Message reçu : {}" ,messageText);

            // Créer une entité MessageEntity pour stocker le message
            MessageEntity messageEntity = new MessageEntity();
            messageEntity.setMessageId(message.getJMSMessageID());
            messageEntity.setContent(messageText);
            messageEntity.setReceivedAt(LocalDateTime.now().toString());

            // Sauvegarder l'entité dans la base de données
            messageRepository.save(messageEntity);
        }
    }
}
