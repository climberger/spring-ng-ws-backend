package de.cl.springngws.backend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
public class MessageRestController {

    private final SimpMessagingTemplate template;

    @Autowired
    MessageRestController(SimpMessagingTemplate template){
        this.template = template;
    }

    @PostMapping(path = "/message", consumes = MediaType.TEXT_PLAIN_VALUE, produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> sendMessage(@RequestBody String message) {

        System.out.println("Received Message From REST Endpoint: " + message);

        String messageToSend = new SimpleDateFormat("HH:mm:ss").format(new Date()) + " - From REST Endpoint: " + message;

        System.out.println("Forward message to /notify: " + messageToSend);

        this.template.convertAndSend("/notify", messageToSend);

        return new ResponseEntity<>(message, HttpStatus.OK);
    }

}
