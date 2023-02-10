package com.example.websub;

import com.joshlong.google.pubsubhubbub.PubsubHubbubClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class WebsubApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebsubApplication.class, args);
    }

    @Bean
    ApplicationListener <ApplicationReadyEvent> ready (PubsubHubbubClient client){
        return new ApplicationListener<ApplicationReadyEvent>() {
            @Override
            public void onApplicationEvent(ApplicationReadyEvent event) {

            }
        } ;
    }
}

@RestController
class WebsubController {

    @RequestMapping(method = {RequestMethod.GET, RequestMethod.POST}, value = "/reset")
    public ResponseEntity<String> handleWebsubSubscriptionAndNotification(
            RequestEntity<String> requestBody,
            @RequestParam(required = false, name = "hub.challenge") String challenge) {

        var string = requestBody.getBody();
        System.out.println("request: " + string);
        if (StringUtils.hasText(challenge))
            return ResponseEntity.ok(challenge);
        return ResponseEntity.status(HttpStatusCode.valueOf(204)).build();

    }
}