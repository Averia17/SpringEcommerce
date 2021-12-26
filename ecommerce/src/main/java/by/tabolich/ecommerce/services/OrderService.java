package by.tabolich.ecommerce.services;

import by.tabolich.ecommerce.model.Order;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class OrderService {
    protected final Log logger = LogFactory.getLog(this.getClass());

    @Autowired
    private JavaMailSender emailSender;

    public void sendMessageAboutOrder(Order order) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("averiasendmail@gmail.com");
        message.setTo(order.getUser().getEmail());
        message.setSubject("Change order №" + order.getId() + " status");
        message.setText("New status " + order.getStatus().getTitle());
        emailSender.send(message);
        logger.info("send email");

    }
}
