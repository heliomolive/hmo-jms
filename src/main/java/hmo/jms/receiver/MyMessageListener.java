package hmo.jms.receiver;

import hmo.jms.config.HmoJmsMessageConverter;
import hmo.jms.config.JmsConfig;
import hmo.jms.model.dto.Queue1Dto;
import lombok.extern.log4j.Log4j2;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;

@Log4j2
@Component
public class MyMessageListener {

    private HmoJmsMessageConverter<Queue1Dto> messageConverter =
            new HmoJmsMessageConverter<>(Queue1Dto.class);

    @JmsListener(destination = "${"+JmsConfig.MY_QUEUE_1+"}")
    public void listen(Message jmsMessage) throws JMSException {
        Queue1Dto queue1Dto = messageConverter.fromMessage(jmsMessage);

        log.info("JmsMessageId: {}. Is it redelivery? {}. Object: {}",
                jmsMessage.getJMSMessageID(),
                jmsMessage.getJMSRedelivered(),
                queue1Dto);
    }
}
