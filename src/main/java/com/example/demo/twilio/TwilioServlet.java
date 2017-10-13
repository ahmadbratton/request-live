package com.example.demo.twilio;
//
//
//
//import com.twilio.twiml.Body;
//import com.twilio.twiml.MessagingResponse;
//import com.twilio.twiml.Message;
//import static spark.Spark.post;
//
///**
// * Created by duhlig on 8/16/17.
// */
//
//public class RecieveSms {
//    public static void main(String[] args) {
//        post("/receive-sms", (req, res) -> {
//           Message sms = new Message.Builder()
//                    .body(new Body("sup"))
//                    .build();
//            MessagingResponse twiml = new MessagingResponse.Builder()
//                    .message(sms)
//                    .build();
//            return twiml.toXml();
//        });
//    }
//}

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import com.twilio.twiml.Body;
import com.twilio.twiml.Message;
import com.twilio.twiml.MessagingResponse;
import com.twilio.twiml.TwiMLException;

public class TwilioServlet extends HttpServlet {

    // service() responds to both GET and POST requests.
    // You can also use doGet() or doPost()
    public void service(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Message sms =
                new Message.Builder().body(new Body("The Robots are coming! Head for the hills!")).build();

        MessagingResponse twiml = new MessagingResponse.Builder().message(sms).build();

        response.setContentType("application/xml");

        try {
            response.getWriter().print(twiml.toXml());
        } catch (TwiMLException e) {
            e.printStackTrace();
        }
    }
}