package hmo.jms.config;

import org.springframework.jms.core.JmsTemplate;

import javax.jms.ConnectionFactory;

public class HmoJmsTemplate<T> extends JmsTemplate {

    public HmoJmsTemplate(String queueName, Class<T> payloadMessageType,
                          ConnectionFactory connectionFactory) {
        super(connectionFactory);
        this.setDefaultDestinationName(queueName);
        this.setMessageConverter(
                new HmoJmsMessageConverter<>(payloadMessageType) );
    }

}
