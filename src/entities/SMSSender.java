/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

/**
 *
 * @author amina
 */
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

//import com.twilio.Twilio;
//import com.twilio.rest.api.v2010.account.Message;
//import com.twilio.type.PhoneNumber;
public class SMSSender {

    // Twilio API credentials
    public static final String ACCOUNT_SID = "AC26099e79db56fd793e742b38716ad6ad";
    public static final String AUTH_TOKEN = "2a306aca57d75531a19a3a7326789407";

    // Phone numbers for the sender and recipient
    public static final String FROM_NUMBER = "+16315294203";
    public static final String TO_NUMBER = "+21658049501";

    public static void sendSMS(String messageBody) {
        System.out.println("Sending SMS...");
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        Message message = Message.creator(new PhoneNumber(TO_NUMBER),
                new PhoneNumber(FROM_NUMBER), messageBody)
                .create();

        System.out.println("SMS sent successfully!");
    }

}