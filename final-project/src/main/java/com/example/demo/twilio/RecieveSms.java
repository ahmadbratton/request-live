package com.example.demo.twilio;



import com.twilio.twiml.Body;
import com.twilio.twiml.MessagingResponse;
import com.twilio.twiml.Message;
import static spark.Spark.post;

/**
 * Created by duhlig on 8/16/17.
 */

public class RecieveSms {
    public static void main(String[] args) {
        post("/receive-sms", (req, res) -> {
           Message sms = new Message.Builder()
                    .body(new Body("sup"))
                    .build();
            MessagingResponse twiml = new MessagingResponse.Builder()
                    .message(sms)
                    .build();
            return twiml.toXml();
        });
    }
}


