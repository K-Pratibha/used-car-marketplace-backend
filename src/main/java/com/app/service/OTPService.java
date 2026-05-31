package com.app.service;

import com.app.payload.OTPDetails;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@Service
public class OTPService {

    private final Map<String, OTPDetails> OTPStorage = new HashMap<>();
    private static final int OTP_EXPIRY_TIME = 5;   //OTP expiration time in minutes

    public String generateOTP(String mobileNumber){
        // generate a random 6 digit OTP
        String otp = String.format("%06d", new Random().nextInt(999999));

        // create an OTPDetails object
        OTPDetails otpDetails = new OTPDetails(otp,System.currentTimeMillis());

        // store the OTP and its timestamp in the storage
        //hashmap algorithm used, mobile --> key , value --> otpDetails(otp,time when the OTP was created)
        OTPStorage.put(mobileNumber, otpDetails);

        return otp;
    }

    //validate the otp
    public boolean validateOTP(String mobileNumber, String otp){
        OTPDetails otpDetails = OTPStorage.get(mobileNumber);

        if (otpDetails == null){
            return false; //    otp not generated
        }

        //check if OTP is expired
        long currentTime = System.currentTimeMillis();
        long otpTime = otpDetails.getTimestamp();
        long timeDifference = TimeUnit.MILLISECONDS.toMinutes(currentTime-otpTime);

        if (timeDifference > OTP_EXPIRY_TIME){
            OTPStorage.remove(mobileNumber);    //remove OTP from storage(hashmap)
            return false; //    otp expired
        }

        //validate OTP
        return otpDetails.getOtp().equals(otp);
    }

}
