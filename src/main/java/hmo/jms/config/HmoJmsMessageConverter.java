package hmo.jms.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.jms.support.converter.MessageConversionException;
import org.springframework.jms.support.converter.MessageConverter;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

@Log4j2
public class HmoJmsMessageConverter<T> implements MessageConverter {

    private ObjectMapper objectMapper = new ObjectMapper();
    private Class<T> type;

    public HmoJmsMessageConverter(Class<T> type) {
        this.type = type;
    }

    @Override
    public Message toMessage(Object object, Session session) throws JMSException, MessageConversionException {
        if (!object.getClass().equals(type)) {
            throw new MessageConversionException(String.format(
                    "Invalid object type [%s], expected [%s].", object.getClass(), type));
        }

        try {
            String payload = objectMapper.writeValueAsString((T)object);
            log.debug("Object [{}] converted to JSON format [{}].", type, payload);

            return session.createTextMessage(payload);

        } catch (JsonProcessingException e) {
            throw new MessageConversionException(String.format(
                    "Error converting object [%s] to JSON format.", type));
        }
    }

    @Override
    public T fromMessage(Message message) throws JMSException, MessageConversionException {
        TextMessage textMessage = (TextMessage) message;
        String payload = textMessage.getText();

        try {
            T object = objectMapper.readValue(payload, type);
            log.debug("Object [{}] parsed from text message [{}].", type, payload);

            return object;

        } catch (JsonProcessingException e) {
            throw new MessageConversionException(String.format(
                    "Error converting text message [%s] to object [%s].", payload, type));
        }
    }
}
