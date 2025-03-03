package hieu.javarestapi.controller;

import hieu.javarestapi.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class EmailController {

    private final EmailService emailService;

    @GetMapping("/send-email")
    public Map<String, Object> sendEmail(@RequestParam String to, @RequestParam String subject, @RequestParam String message) throws IOException {
        Map<String, Object> result = new HashMap<>();
        String returnMsg = emailService.send(to, subject, message);

        result.put("message", returnMsg);
        result.put("status", HttpStatus.OK);
        return result;
    }
}
