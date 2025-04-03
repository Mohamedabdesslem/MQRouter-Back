package com.bank.MQRouter.model;

import lombok.Data;
import javax.persistence.*;

@Entity
@Table(name = "messages")
@Data
public class MessageEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "message_id", nullable = false)
    private String messageId;

    @Column(name = "content", columnDefinition = "TEXT")
    private String content;

    @Column(name = "received_at")
    private String receivedAt;
}
