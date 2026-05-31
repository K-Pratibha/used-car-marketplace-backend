package com.app.config;

import com.twilio.Twilio;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TwilioConfig {

    @Value("${twilio.account.sid}")
    private String accountSid;

    @Value("${twilio.auth.token}")
    private String authToken;

    @Value("${twilio.phone.number}")
    private String twilioPhoneNumber;

    @Value("${twilio.whatsapp.number}")
    private String twilioWhatsappNumber;

    //this will help in performing login twilio operations
    @Bean
    public void init(){
        Twilio.init(accountSid, authToken);
    }

    public String getTwilioPhoneNumber(){
        return twilioPhoneNumber;
    }

    public String getTwilioWhatsappNumber(){
        return twilioWhatsappNumber;
    }

}
