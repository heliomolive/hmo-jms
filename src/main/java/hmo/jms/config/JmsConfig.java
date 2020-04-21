package hmo.jms.config;

import hmo.jms.model.dto.Queue1Dto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.jms.ConnectionFactory;

@Configuration
@EnableScheduling
public class JmsConfig {

    public static final String MY_QUEUE_1 = "myqueue1";

    @Autowired
    private ConnectionFactory connectionFactory;

    @Value("${"+MY_QUEUE_1+"}")
    private String queue1;

    @Bean("queue1JmsTemplate")
    public JmsTemplate getQueue1JmsTemplate() {
        return new HmoJmsTemplate<>(queue1, Queue1Dto.class, connectionFactory);
    }
}
