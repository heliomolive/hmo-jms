package hmo.jms.sender;

import hmo.jms.model.dto.Queue1Dto;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Random;
import java.util.UUID;

@Log4j2
@Component
public class MyMessageSender {

    @Autowired
    @Qualifier("queue1JmsTemplate")
    private JmsTemplate hmoJmsTemplate;

    private Random random = new Random();

    @Scheduled(fixedRate = 5000)
    public void sendMessageToQueue1() {
        Queue1Dto object = create1Dto();
        log.info("Sending message [{}]", object);

        /*
        Setting JmsTemplate as "transacted" will use a short local JMS transaction when running
        outside of a managed transaction, and a synchronized local JMS transaction in case of a
        managed transaction (other than an XA transaction) being present. This has the effect of
        a local JMS transaction being managed alongside the main transaction (which might be a
        native JDBC transaction), with the JMS transaction committing right after the main
        transaction.

        hmoJmsTemplate.setSessionTransacted(true);
         */

        hmoJmsTemplate.convertAndSend(object);
    }

    private Queue1Dto create1Dto() {
        int n = random.nextInt(1000);
        return Queue1Dto.builder()
                .uuid(UUID.randomUUID())
                .title("Queue1Dto "+n)
                .description("Description for Queue1Dto "+n)
                .build();
    }
}
