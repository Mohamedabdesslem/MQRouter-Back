package com.bank.MQRouter.service;

import com.ibm.mq.jms.MQConnectionFactory;
import com.ibm.msg.client.wmq.WMQConstants;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;

@Configuration
public class MQConfig {

    @Bean
    public ConnectionFactory connectionFactory() throws JMSException {
        MQConnectionFactory factory = new MQConnectionFactory();
        factory.setHostName("your-host");
        factory.setPort(1414);
        factory.setQueueManager("QM1");
        factory.setChannel("CHANNEL.NAME");
        factory.setTransportType(WMQConstants.WMQ_CM_CLIENT);
        return factory;
    }

    @Bean
    public DefaultJmsListenerContainerFactory jmsListenerContainerFactory(ConnectionFactory connectionFactory) {
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        return factory;
    }
}
