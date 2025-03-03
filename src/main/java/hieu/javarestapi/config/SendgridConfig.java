package hieu.javarestapi.config;

import com.sendgrid.SendGrid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

@Configurable
@RequiredArgsConstructor
public class SendgridConfig {

    @Value("${spring.sendgrid.api-key}")
    private final String secretKey;

    @Bean
    public SendGrid sendGrid() {
        return new SendGrid(secretKey);
    }
}
