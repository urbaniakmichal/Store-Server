package practice.bank.rabbit.mail;

import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

@PropertySource("classpath:rabbitBank.properties")
@Service
@Log4j2
public class SenderMailService {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Value("${queue.from.bank.to.email}")
    private String queueFromBankToEmail;


    public void send(SenderMailPayload senderMailPayload) {
        log.info("Send mailPayload object to mail-service. Payload: {}", senderMailPayload);
        rabbitTemplate.convertAndSend(queueFromBankToEmail, senderMailPayload);
    }
}
