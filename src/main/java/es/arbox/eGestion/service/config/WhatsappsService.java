package es.arbox.eGestion.service.config;

import org.springframework.stereotype.Service;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;

@Service
public class WhatsappsService {

	private static final String ACCOUNT_SID = "ACac627bde44ae48447b768fd7515932c6"; 
	private static final String AUTH_TOKEN = "0736e6834831815b36b7b33b9585636f"; 
    
	public Message enviarMensaje(String mensaje, String para) {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN); 
        Message message = Message.creator( 
                new com.twilio.type.PhoneNumber("whatsapp:"+para), 
                new com.twilio.type.PhoneNumber("whatsapp:+14155238886"),  
                mensaje)      
            .create();
		return message;
	}
}
