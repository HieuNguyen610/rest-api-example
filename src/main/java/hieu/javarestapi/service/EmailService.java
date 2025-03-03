package hieu.javarestapi.service;

import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class EmailService {

    @Value("${sendgrid.fromEmail}")
    private String fromEmail;
    private final SendGrid sendgrid;

    public String send(String toEmail, String subject, String message) throws IOException {

        Email from = new Email(fromEmail);
        Email to = new Email(toEmail);
        Content content = new Content("text/plain", message);

        Mail mail = new Mail(from, subject, to, content);

        Request request = new Request();
        request.setMethod(Method.POST);
        request.setBody(mail.build());
        request.setEndpoint("mail/send");

        Response response = sendgrid.api(request);

        if (response.getStatusCode() == 202) {
            return "Email sent successfully";
        } else {
            return "Failed to send email: " + response.getBody();
        }
    }
}
