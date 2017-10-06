package com.example.demo.twilio;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

/**
 * Created by duhlig on 8/16/17.
 */
public class Sendsms {
    public static final String ACCOUNT_SID = "ACdceca3a759257709024d917d126f4b35";
    public static final String AUTH_TOKEN = "ff1c8ee32b49fe2cbe918637fd179f9c";

    public static void main(String[] args) {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        Message message = Message.creator(
                new PhoneNumber("+14045505646"),
                new PhoneNumber("6784308676"),
                "it worked yaay!"
        ).create();
    }
}
