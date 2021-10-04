package es.arbox.eGestion.converter;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

public class StringDateConverter extends JsonDeserializer<Date> {
	
	@Override
	public Date deserialize(JsonParser jsonparser, DeserializationContext context) throws IOException, JsonProcessingException {
	  SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
	  String date = jsonparser.getText();
	  try {
	      return formatter.parse(date);
	  } catch (ParseException e) {
	      throw new RuntimeException(e);
	  }
	}
}
