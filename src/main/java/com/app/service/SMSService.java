package com.app.service;

import com.app.config.TwilioConfig;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SMSService {

    private final TwilioConfig twilioConfig;

    @Autowired
    public SMSService(TwilioConfig twilioConfig) {
        this.twilioConfig = twilioConfig;
    }

    //SMS
    public void sendSMS(String mobileNumber, String message) {
        // Use Twilio API to send SMS
        //Message.creator helps to create messages to send
        Message.creator(
                new PhoneNumber(mobileNumber),  //supply here number (receiver)
                new PhoneNumber(twilioConfig.getTwilioPhoneNumber()),   //supply here Twilio's number (sms sender)
                message     //write your message here
        ).create();
    }

    //WhatsApp message sending
    public void sendWhatsApp(String mobileNumber, String message) {
        // Use Twilio API to send SMS
        //Message.creator helps to create messages to send
        Message.creator(
                new PhoneNumber("whatsapp:" + mobileNumber),  //supply here number
                new PhoneNumber("whatsapp:+14155238886"),   //supply here Twilio Whatsapp number
                message     //write your message here
        ).create();
    }
}

